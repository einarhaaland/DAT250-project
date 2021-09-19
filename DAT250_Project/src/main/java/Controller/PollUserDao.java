package Controller;

import Model.PollUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PollUserDao {

    private List<PollUser> users = new ArrayList<>();

    public Optional<PollUser> get(int id) {
        return Optional.ofNullable(users.get(id));
    }

    public List<PollUser> getAll() {
        return users;
    }

    public void save(PollUser user) {
        users.add(user);
    }

    public void update(PollUser user, String[] params) {

        user.setUsername(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        user.setPassword(Objects.requireNonNull(
                params[1], "Password cannot be null"));

        users.add(user);
    }

    public void delete(PollUser user) {
        users.remove(user);
    }
}
