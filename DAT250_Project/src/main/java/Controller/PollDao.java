package Controller;

import Model.Poll;
import Test.JPATest;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class PollDao {

    //private static final String PERSISTENCE_UNIT_NAME = "user";
    private static List<Poll> pollList;
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(JPATest.PERSISTENCE_UNIT_NAME);
    private void getFactory(EntityManagerFactory fac) {
        factory = fac;
    }

    private EntityManager em = factory.createEntityManager();

/*
    public Optional<Poll> get(int id) {
        return Optional.ofNullable(em.find(Poll.class, id));
    }
*/

    public Poll get(int id) {
        return em.find(Poll.class, id);
    }

    public List<Poll> getAll() {
        Query query = em.createQuery("SELECT p FROM Poll p");
        return query.getResultList();
    }


    public void save(Poll poll) {
        executeInsideTransaction(entityManager -> entityManager.persist(poll));
    }


    public void update(Poll poll, String params) {
        poll.setQuestion(Objects.requireNonNull(params, "Question cannot be null."));
        executeInsideTransaction(em -> em.merge(poll));
    }


    public void delete(Poll poll) {
        executeInsideTransaction(em -> em.remove(poll));
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
