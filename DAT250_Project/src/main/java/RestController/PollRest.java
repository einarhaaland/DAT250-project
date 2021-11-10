package RestController;



import Controller.JpaPollUserDao;
import Controller.PollDao;
import Controller.VoteDao;
import Model.Poll;
import Model.PollUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@RestController
public class PollRest {

    private static EntityManagerFactory factory;

    //DAO instances
    PollDao pollService = new PollDao();
    VoteDao voteService = new VoteDao();
    JpaPollUserDao userService = new JpaPollUserDao();

    @GetMapping("/polls")
    List<Poll> getAll() {
        return pollService.getAll();
    }

    @PostMapping("/user/{id}/poll")
    Poll newPoll(@RequestBody Poll poll, @PathVariable int id) {

        PollUser user = userService.get(id);

        return pollService.save(poll, user);
    }

    @GetMapping("/poll/{id}")
    Poll get(@PathVariable int id) {
        return pollService.get(id);
    }

    @DeleteMapping("/poll/{id}")
    void delete(@PathVariable int id) {

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
}
