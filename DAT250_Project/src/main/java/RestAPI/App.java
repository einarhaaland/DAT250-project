package RestAPI;

import Controller.JpaPollUserDao;
import Controller.PollDao;
import Controller.VoteDao;
import Model.*;
import Test.JPATest;
import com.google.gson.Gson;
import com.mongodb.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.net.UnknownHostException;
import java.util.List;

import static Globals.Globals.PERSISTENCE_UNIT_NAME;
import static spark.Spark.*;

public class App {

    private static EntityManagerFactory factory;

    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        //Entity Manager
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
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

        //TODO: Upload results to mongoDB using MQTT
        /**
         *
         * Q: Kan vi lage knapp i frontend som sender json representasjon av poll resultat?
         *
         */
        post("/results", (request, response) -> {
            response.type("application/json");
            Result result = new Gson().fromJson(request.body(), Result.class);



            return result.toJson();
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
/*
        try {
            // Create new instance of mongoclient
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

            // get and/or create db and collection
            DB database = mongoClient.getDB("PollResults");
            DBCollection collection = database.getCollection("Results");

            post("/polls/:id/votes", (request, response) -> {
                response.type("application/json");
                Vote vote = new Gson().fromJson(request.body(), Vote.class);


                if (vote.getVote() == VoteE.YES) {
                    collection.update();
                } else if (vote.getVote() == VoteE.NO) {
                    collection.update();
                }
                //DBObject object = new BasicDBObject("_id", voteId)


                return collection.insert(object);

            });


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
*/

        em.close();

    }

}