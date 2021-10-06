package Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "pollUser")
public class PollUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;

    public PollUser (String username, String password) {
        this.username = username;
        this.password = password;
    }

    @OneToMany()
    public final List<Poll> polls = new ArrayList<Poll>();

    @OneToMany()
    public final List<Vote> votes = new ArrayList<Vote>();

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }


//    public List<Poll> getPolls() { return polls; }

//    public List<Vote> getVotes() {
//        return votes;
//    }
}
