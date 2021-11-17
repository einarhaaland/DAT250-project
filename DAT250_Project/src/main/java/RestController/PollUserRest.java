package RestController;


import Controller.JpaPollUserDao;
import Controller.PollDao;
import Controller.VoteDao;
import Model.Poll;
import Model.PollUser;

import Model.Vote;
import Security.registration.RegistrationRequest;
import Security.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PollUserRest {

    //DAO instances
    JpaPollUserDao userService = new JpaPollUserDao();
    PollDao pollService = new PollDao();
    VoteDao voteService = new VoteDao();

    //Registration service
    private RegistrationService registrationService;

    @PostMapping("/registration")
    public PollUser register(@RequestBody PollUser user) {
        return registrationService.register(user);
    }

    //PollUser REST
    @GetMapping("/users")
    List<PollUser> getAll() {
        return userService.getAll();
    }

    @PostMapping("/users")
    PollUser newUser(@RequestBody PollUser user) {
        return userService.save(user);
    }

    @GetMapping("/user/{id}")
    PollUser get(@PathVariable int id) {
        return userService.get(id);
    }

    @PutMapping("/users/{id}")
    PollUser put(@RequestBody PollUser newUser, @PathVariable int id) {

        PollUser updated = userService.get(id);
        PollUser editedTodo = userService.update(newUser, updated);

        return editedTodo;
    }

    @DeleteMapping("/user/{id}")
    void delete(@PathVariable int id) {
        userService.delete(id);
    }

    //Poll REST
    @GetMapping("/polls")
    List<Poll> getAllPolls() {
        return pollService.getAll();
    }

    @PostMapping("/user/{id}/polls")
    Poll newPoll(@RequestBody Poll poll, @PathVariable int id) {

        PollUser user = userService.get(id);

        return pollService.save(poll, user);
    }

    @GetMapping("/poll/{id}")
    Poll getPoll(@PathVariable int id) {
        return pollService.get(id);
    }

    @DeleteMapping("/poll/{id}")
    void deletePoll(@PathVariable int id) {

        Poll poll = pollService.get(id);
        List<PollUser> list = userService.getAll();

        //TODO: Erstatte med hashmap
        for (PollUser user : list) {
            for (Poll p : user.polls) {
                if (p.getId() == id) {
                    pollService.removePoll(user, p);
                }
            }
        }

        pollService.delete(poll);
    }

    //Vote REST
    @GetMapping("/polls/{id}/votes")
    List<Vote> getAllVotes(@PathVariable int id) {

        Poll poll = pollService.get(id);
        List<Vote> list = poll.getVotes();
        return list;
    }

    @PostMapping("/polls/{id}/votes")
    Vote newVote(@RequestBody Vote vote, @PathVariable int id) {
        Poll poll = pollService.get(id);
        return voteService.save(vote, poll);
    }
}
