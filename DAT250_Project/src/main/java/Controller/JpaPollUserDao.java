package Controller;

import Model.PollUser;
import Test.JPATest;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static Globals.Globals.PERSISTENCE_UNIT_NAME;

public class JpaPollUserDao {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private EntityManager em = factory.createEntityManager();

    //Login
    public PollUser login(PollUser user) {

        List<PollUser> list = getAll();

        for (PollUser p : list) {
            if (p.getUsername().equals(user.getUsername()) && p.getPassword().equals(user.getPassword())) {
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
