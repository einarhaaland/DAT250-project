package RestAPI;

import Controller.JpaPollUserDao;
import Controller.PollDao;
import Controller.VoteDao;
import Model.Poll;
import Model.PollUser;
import Model.Vote;
import com.google.gson.Gson;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        //DAO instances
        PollDao pollService = new PollDao();
        VoteDao voteService = new VoteDao();
        JpaPollUserDao userService = new JpaPollUserDao();

        //CREATE
        post("/polls", (request, response) -> {
            response.type("application/json");
            Poll poll = new Gson().fromJson(request.body(), Poll.class);
            pollService.save(poll);
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


        //UPDATE
        /*
        put("/users/:id", (request, response) -> {
            response.type("application/json");
            PollUser toEdit = new Gson().fromJson(request.body(), PollUser.class);
            PollUser editedTodo = userService.update(toEdit);

            if (editedTodo != null) {
                return new Gson().toJsonTree(editedTodo);
            } else {
                return new Gson().toJson(" not found or error in edit");
            }
        });
        */

        //DELETE
        delete("/user/:id", (request, response) -> {
            response.type("application/json");
            userService.delete(userService.get(Integer.parseInt(request.params(":id"))));
            return new Gson().toJson("user deleted");
        });

    }

}
