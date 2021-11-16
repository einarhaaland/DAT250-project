package RestController;


import Controller.JpaPollUserDao;
import Controller.PollDao;
import Controller.VoteDao;
import Model.PollUser;
import Test.JPATest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PollUserRest {

    private static EntityManagerFactory factory;

    //DAO instances
    PollDao pollService = new PollDao();
    VoteDao voteService = new VoteDao();
    JpaPollUserDao userService = new JpaPollUserDao();

    @GetMapping("/users")
    List<PollUser> getAll() {
        return userService.getAll();
    }

    @PostMapping("/users")
    PollUser newUser(@RequestBody PollUser user) {
        return userService.save(user);
    }

    @GetMapping("/user/{id")
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
}
