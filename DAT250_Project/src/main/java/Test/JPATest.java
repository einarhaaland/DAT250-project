package Test;

import static Globals.Globals.PERSISTENCE_UNIT_NAME;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import Controller.JpaPollUserDao;
import Controller.PollDao;
import Controller.PollUserDao;

import Controller.VoteDao;
import Globals.Globals;
import Model.PollUser;
import Model.VoteE;
import org.junit.Before;
import org.junit.Test;

import Model.Poll;
import Model.Vote;

import java.util.ArrayList;
import java.util.List;

public class JPATest {

    //public static final String PERSISTENCE_UNIT_NAME = "users";
    private EntityManagerFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        // Begin a new local transaction so that we can persist a new entity
        em.getTransaction().begin();



        JpaPollUserDao PUController = new JpaPollUserDao();
        VoteDao VController = new VoteDao();
        PollDao PController = new PollDao();



        /*
        // read the existing entries
        Query q = em.createQuery("select m from PollUser m");
        // Persons should be empty TODO: but is only the first time running
        */

        List<PollUser> users = PUController.getAll();


        // do we have entries?
        //boolean createNewEntries = (q.getResultList().size() == 0);

        boolean createNewEntries = users.size() == 0;

        // No, so lets create new entries TODO: Only one new entry after first run
        if (createNewEntries) {
            assertTrue(users.size() == 0);


            PollUser user = new PollUser("Family for the Knopfs", "passord1");

            Poll poll = new Poll();
            poll.setQuestion("Knopf_");

            Vote vote = new Vote();
            vote.setVote(VoteE.YES);

            // now persists the family person relationship
            user.polls.add(poll);
            user.votes.add(vote);

            PController.save(poll, user);
            //VController.save(vote);
            PUController.save(user);

            /*
            em.persist(user);
            em.persist(vote);
            em.persist(poll);
            */

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

        // We should have 40 Persons in the database TODO: changed to 1 due to the program only creating one new entry
        assertTrue(q.getResultList().size() >= 1);

        em.close();
    }



    @Test(expected = javax.persistence.NoResultException.class) //TODO: korleis funker denne?
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
