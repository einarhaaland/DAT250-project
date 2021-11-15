package RestController;

import Controller.JpaPollUserDao;
import Controller.PollDao;
import Controller.VoteDao;
import Model.Poll;
import Model.Vote;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@RestController
@CrossOrigin("*")
public class VoteRest {

    private static EntityManagerFactory factory;

    //DAO instances
    PollDao pollService = new PollDao();
    VoteDao voteService = new VoteDao();
    JpaPollUserDao userService = new JpaPollUserDao();

    @GetMapping("/polls/{id}/votes")
    List<Vote> getAll(@PathVariable int id) {

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
