package RestAPI;

import Controller.JpaPollUserDao;
import Controller.PollDao;
import Controller.VoteDao;
import Model.Poll;
import Model.PollUser;
import Model.Vote;
import Test.JPATest;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static spark.Spark.*;

//@SpringBootApplication
public class App {

    private static EntityManagerFactory factory;

    public static void main(String[] args) {

        //SpringApplication.run(App.class, args);

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        //Entity Manager TODO: lagre PUN (haha) ein anna plass enn JPATest
        factory = Persistence.createEntityManagerFactory(JPATest.PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        //DAO instances
        PollDao pollService = new PollDao();
        VoteDao voteService = new VoteDao();
        JpaPollUserDao userService = new JpaPollUserDao();

        //CREATE
        post("/user/:id/polls", (request, response) -> {
            response.type("application/json");
            Poll poll = new Gson().fromJson(request.body(), Poll.class);
            PollUser user = userService.get(Integer.parseInt(request.params(":id")));
            pollService.save(poll, user);
            return poll.toJson();
        });

        //TODO: vote må få riktig poll
        post("/polls/:id/votes", (request, response) -> {
            response.type("application/json");
            Vote vote = new Gson().fromJson(request.body(), Vote.class);
            Poll p = pollService.get(Integer.parseInt(request.params(":id")));
            voteService.save(vote, p);
            return vote.toJson();
        });


        post("/users", (request, response) -> {
            response.type("application/json");
            PollUser user = new Gson().fromJson(request.body(), PollUser.class);
            userService.save(user);
            return user.toJson();
        });


        //READ
        //polls
        get("/polls", (request, response) -> {
            response.type("application/json");
            return new Gson().toJsonTree(pollService.getAll());
        });
        get("/polls/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJsonTree(pollService.get(Integer.parseInt(request.params(":id"))));
        });

        //users
        get("/users", (request, response) -> {
            response.type("application/json");
            return new Gson().toJsonTree(userService.getAll());
        });
        get("/users/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJsonTree(userService.get(Integer.parseInt(request.params(":id"))));
        });

        //votes
        get("/polls/:id/votes", (request, response) -> {
            response.type("application/json");
            Poll p = pollService.get(Integer.parseInt(request.params(":id")));
            return new Gson().toJsonTree(p.getVotes());
        });

        //DELETE
        delete("/polls/:id", (request, response) -> {
            response.type("application/json");
            Poll poll = pollService.get(Integer.parseInt(request.params(":id")));
            List<PollUser> list = userService.getAll();

            //TODO: Erstatte med hashmap
            for (PollUser user : list) {
               for (Poll p : user.polls) {
                   if (p.getId() == Integer.parseInt(request.params(":id"))) {
                       pollService.removePoll(user, p);
                   }
               }
            }

            pollService.delete(poll);
            return new Gson().toJson("Successfully deleted poll: " + request.params(":id"));
        });

        delete("/user/:id", (request, response) -> {
            response.type("application/json");
            userService.delete(userService.get(Integer.parseInt(request.params(":id"))));
            return new Gson().toJson("Successfully delete user: " + request.params(":id"));
        });

        /*
        delete("/polls/:id/vote/:id", (request, response) -> {
           response.type("application/json");
           Poll p = pollService.get(Integer.parseInt(request.params(":id")));
           voteService.delete(voteService.get(Integer.parseInt(request.params(":id"))), p);
           return new Gson().toJson("Successfully delete vote: " + request.params(":id"));
        }); */

        //UPDATE
        put("/users/:id", (request, response) -> {
            response.type("application/json");
            PollUser toEdit = new Gson().fromJson(request.body(), PollUser.class);
            PollUser updated = userService.get(Integer.parseInt(request.params(":id")));
            PollUser editedTodo = userService.update(toEdit, updated);

            if (editedTodo != null) {
                return new Gson().toJsonTree(editedTodo);
            } else {
                return new Gson().toJson(" not found or error in edit");
            }
        });



        em.close();

    }

}