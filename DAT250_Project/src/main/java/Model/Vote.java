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
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    private VoteE vote;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public VoteE getVote() { return vote; }

    public void setVote(VoteE vote) { this.vote = vote; }

    public String toJson() {
        Gson gson = new Gson();
        String representation = gson.toJson(this);
        return representation;
    }
}
