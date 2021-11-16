package RestController;


import Controller.JpaPollUserDao;
import Model.PollUser;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PollUserRest {

    //DAO instances
    JpaPollUserDao userService = new JpaPollUserDao();

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
}
