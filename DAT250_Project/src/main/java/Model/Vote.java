package Model;

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
    private boolean vote;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public boolean getVote() { return vote; }

    public void setVote(boolean vote) { this.vote = vote; }
}
