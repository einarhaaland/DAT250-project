package RestAPI;

import Controller.JpaPollUserDao;
import Controller.PollDao;
import Controller.VoteDao;
import MessagingSystems.Messaging;
//import MessagingSystems.MongoService;
import MessagingSystems.MongoService;
import Model.*;
import Test.JPATest;
import com.google.gson.Gson;
import com.mongodb.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.net.UnknownHostException;
import java.util.List;

import static Globals.Globals.PERSISTENCE_UNIT_NAME;

import static spark.Spark.*;

public class App {

    private static EntityManagerFactory factory;
    private static Messaging messaging;

    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }


        //Messaging service
        messaging = new Messaging();

        //Entity Manager
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        //DAO instances
        PollDao pollService = new PollDao();
        VoteDao voteService = new VoteDao();
        JpaPollUserDao userService = new JpaPollUserDao();

        //Login
        post("/login", (request, response) -> {
            response.type("application/json");
            PollUser user = new Gson().fromJson(request.body(), PollUser.class);
            return userService.login(user).toJson();
        });

        //CREATE
        post("/user/:id/polls", (request, response) -> {
            response.type("application/json");
            Poll poll = new Gson().fromJson(request.body(), Poll.class);
            PollUser user = userService.get(Integer.parseInt(request.params(":id")));
            pollService.save(poll, user);
            return poll.toJson();
        });

        //TODO: vote m?? f?? riktig poll
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


        post("/results/:id", (request, response) -> {
            response.type("application/json");

            Result r = new Gson().fromJson(request.body(), Result.class);

            Thread t = new Thread(new Messaging());
            t.run();

            messaging.sendResult(r);

            return r.toJson();
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