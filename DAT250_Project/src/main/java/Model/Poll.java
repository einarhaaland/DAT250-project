package Model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    private String question;

    @OneToMany()
    private final List<Vote> votes = new ArrayList<Vote>();

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public List<Vote> getVotes() { return votes; }

    public void addVote(Vote v) {
        votes.add(v);
    }

    public String toJson() {
        Gson gson = new Gson();
        String representation = gson.toJson(this);
        return representation;
    }
}
