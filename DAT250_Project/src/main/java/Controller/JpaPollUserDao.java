package Controller;

import Model.PollUser;
import Test.JPATest;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class JpaPollUserDao {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(JPATest.PERSISTENCE_UNIT_NAME);
    private EntityManager em = factory.createEntityManager();

    public Optional<PollUser> get(int id) {
        return Optional.ofNullable(em.find(PollUser.class, id));
    }

    public List<PollUser> getAll() {
        Query query = em.createQuery("SELECT e from PollUser e");
        return query.getResultList();
    }

    public void save(PollUser user) {
        executeInsideTransaction(em -> em.persist(user));
    }

    public void update(PollUser user, String[] params) {
        user.setUsername(Objects.requireNonNull(params[0], "Username cannot be null"));;
        user.setPassword(Objects.requireNonNull(params[1], "Password cannot be null"));
        executeInsideTransaction(em -> em.merge(user));
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
