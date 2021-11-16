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

    public PollUser get(int id) {
        return em.find(PollUser.class, id);
    }

    public List<PollUser> getAll() {
        Query query = em.createQuery("SELECT e from PollUser e");
        return query.getResultList();
    }

    public PollUser findByUsername(String username) {
        return em.find(PollUser.class, username);
    }

    public PollUser save(PollUser user) {

        executeInsideTransaction(em -> em.persist(user));
        return user;
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

    public void delete(int id) {
        executeInsideTransaction(em -> em.remove(id));
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
