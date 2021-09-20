package Controller;

import Model.Poll;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class PollDao {

    private static List<Poll> pollList;
    private EntityManagerFactory factory;
    private void getFactory(EntityManagerFactory fac) {
        factory = fac;
    }

    private EntityManager entityManager = factory.createEntityManager();

    public Optional<Poll> get(int id) {
        return Optional.ofNullable(entityManager.find(Poll.class, id));
    }


    public List<Poll> getAll() {
        Query query = entityManager.createQuery("SELECT p FROM Poll p");
        return query.getResultList();
    }


    public void save(Poll poll) {
        executeInsideTransaction(entityManager -> entityManager.persist(poll));
    }


    public void update(Poll poll, String params) {
        poll.setQuestion(Objects.requireNonNull(params, "Question cannot be null."));
        executeInsideTransaction(entityManager -> entityManager.merge(poll));
    }


    public void delete(Poll poll) {
        executeInsideTransaction(entityManager -> entityManager.remove(poll));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
