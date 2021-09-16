package Test;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import Model.PollUser;
import org.junit.Before;
import org.junit.Test;

import Model.Poll;
import Model.Vote;

public class JPATest {

    private static final String PERSISTENCE_UNIT_NAME = "user";
    private EntityManagerFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        // Begin a new local transaction so that we can persist a new entity
        em.getTransaction().begin();

        // read the existing entries
        Query q = em.createQuery("select m from PollUser m");
        // Persons should be empty

        // do we have entries?
        boolean createNewEntries = (q.getResultList().size() == 0);

        // No, so lets create new entries
        if (createNewEntries) {
            assertTrue(q.getResultList().size() == 0);
            PollUser user = new PollUser();
            user.setUsername("Family for the Knopfs");
            user.setPassword("passord1");

            Poll poll = new Poll();
            poll.setQuestion("Knopf_");

            Vote vote = new Vote();
            vote.setVote(true);
            // now persists the family person relationship
            user.polls.add(poll);
            user.votes.add(vote);
            em.persist(user);
            em.persist(vote);
            em.persist(poll);

        }

        // Commit the transaction, which will cause the entity to
        // be stored in the database
        em.getTransaction().commit();

        // It is always good practice to close the EntityManager so that
        // resources are conserved.
        em.close();

    }

    @Test
    public void checkAvailablePeople() {

        // now lets check the database and see if the created entries are there
        // create a fresh, new EntityManager
        EntityManager em = factory.createEntityManager();

        // Perform a simple query for all the Message entities
        Query q = em.createQuery("select m from Poll m");

        // We should have 40 Persons in the database
        assertTrue(q.getResultList().size() == 40);

        em.close();
    }



    @Test(expected = javax.persistence.NoResultException.class)
    public void deletePerson() {
        EntityManager em = factory.createEntityManager();
        // Begin a new local transaction so that we can persist a new entity
        em.getTransaction().begin();
        Query q = em
                .createQuery("SELECT p FROM Poll p WHERE p.question = :firstName");
        q.setParameter("firstName", "Jim_1");
        Poll user = (Poll) q.getSingleResult();
        em.remove(user);
        em.getTransaction().commit();
        Poll person = (Poll) q.getSingleResult();
        // Begin a new local transaction so that we can persist a new entity

        em.close();
    }
}
