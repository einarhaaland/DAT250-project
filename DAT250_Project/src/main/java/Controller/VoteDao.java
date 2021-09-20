package Controller;

import Model.Vote;
import Model.VoteE;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class VoteDao {

    private EntityManager entityManager;

    public Optional<Vote> get(long id) {
        return Optional.ofNullable(entityManager.find(Vote.class, id));
    }

    public List<Vote> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Vote e");
        return query.getResultList();
    }

    public void save(Vote vote) {
        executeInsideTransaction(entityManager -> entityManager.persist(vote));
    }

    public void update(Vote vote, VoteE value) {
        vote.setVote(value);
        executeInsideTransaction(entityManager -> entityManager.merge(vote));
    }

    public void delete(Vote vote) {
        executeInsideTransaction(entityManager -> entityManager.remove(vote));
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
