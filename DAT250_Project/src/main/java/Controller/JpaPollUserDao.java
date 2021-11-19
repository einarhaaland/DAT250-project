package Controller;

import Model.PollUser;
import Test.JPATest;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.function.Consumer;

import static Globals.Globals.PERSISTENCE_UNIT_NAME;

public class JpaPollUserDao {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private EntityManager em = factory.createEntityManager();

    // generates the Salt value. It can be stored in a database.
    String saltvalue = PassBasedEnc.getSaltvalue(30);

    //Login
    public PollUser login(PollUser user) {

        List<PollUser> list = getAll();

        for (PollUser p : list) {
            if (p.getUsername().equals(user.getUsername()) && PassBasedEnc.verifyUserPassword(user.getPassword(),p.getPassword(),saltvalue)) {
                return get(p.getId());
            }
        }
        throw new IllegalStateException("Username or password is incorrect");
    }

    public PollUser get(int id) {
        return em.find(PollUser.class, id);
    }

    public List<PollUser> getAll() {
        Query query = em.createQuery("SELECT e from PollUser e");
        return query.getResultList();
    }

    public void save(PollUser user) {

        List<PollUser> list = getAll();

        for (PollUser p : list) {
            if (p.getUsername().equals(user.getUsername())) {
                throw new IllegalStateException("User already exists");
            }
        }

        // Password-Based Encryption using Salt and Base64
        String encryptedPassword = PassBasedEnc.generateSecurePassword(user.getPassword(), saltvalue);

        user.setPassword(encryptedPassword);

        executeInsideTransaction(em -> em.persist(user));
    }

    public PollUser update(PollUser user, PollUser old) {
        if (user.getUsername() != null){
            old.setUsername(user.getUsername());
        }
        if (user.getPassword()!= null){
            old.setPassword(user.getPassword());
        }
        executeInsideTransaction(em -> em.merge(old));
        return old;
    }

    public void delete(PollUser user) {
        executeInsideTransaction(em -> em.remove(user));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}

class PassBasedEnc
{
    /* Declaration of variables */
    private static final Random random = new SecureRandom();
    private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int iterations = 10000;
    private static final int keylength = 256;

    /* Method to generate the salt value. */
    public static String getSaltvalue(int length)
    {
        StringBuilder finalval = new StringBuilder(length);

        for (int i = 0; i < length; i++)
        {
            finalval.append(characters.charAt(random.nextInt(characters.length())));
        }

        return new String(finalval);
    }

    /* Method to generate the hash value */
    public static byte[] hash(char[] password, byte[] salt)
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keylength);
        Arrays.fill(password, Character.MIN_VALUE);
        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
        finally
        {
            spec.clearPassword();
        }
    }

    /* Method to encrypt the password using the original password and salt value. */
    public static String generateSecurePassword(String password, String salt)
    {
        String finalval = null;

        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        finalval = Base64.getEncoder().encodeToString(securePassword);

        return finalval;
    }

    /* Method to verify if both password matches or not */
    public static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword, String salt)
    {
        boolean finalval = false;

        /* Generate New secure password with the same salt */
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        /* Check if two passwords are equal */
        finalval = newSecurePassword.equalsIgnoreCase(securedPassword);

        return finalval;
    }
}
