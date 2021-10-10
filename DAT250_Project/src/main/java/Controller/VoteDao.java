package Controller;

import Model.Poll;
import Model.Vote;
import Model.VoteE;
import Test.JPATest;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class VoteDao {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory(JPATest.PERSISTENCE_UNIT_NAME);
    private EntityManager em = factory.createEntityManager();;

    /*public Optional<Vote> get(long id) {
        return Optional.ofNullable(em.find(Vote.class, id));
    } */

    public List<Vote> getAll() {
        Query query = em.createQuery("SELECT e FROM Vote e");
        return query.getResultList();
    }

    public Vote get(int id) { return em.find(Vote.class, id);}

    public void save(Vote vote, Poll poll) {
        poll.addVote(vote);
        executeInsideTransaction(em -> em.persist(vote));
        executeInsideTransaction(em -> em.merge(poll));
    }

    public void update(Vote vote, VoteE value) {
        vote.setVote(value);
        executeInsideTransaction(entityManager -> entityManager.merge(vote));
    }

    public void delete(Vote vote, Poll poll) {
        poll.removeVote(vote);
        executeInsideTransaction(entityManager -> entityManager.remove(vote));
        executeInsideTransaction(em -> em.merge(poll));
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
