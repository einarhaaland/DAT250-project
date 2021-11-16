package Model;

import Security.AppUserRole;
import com.google.gson.Gson;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "pollUser")
public class PollUser  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;

    private AppUserRole role;


    public PollUser(String username, String password, AppUserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @OneToMany()
    public final List<Poll> polls = new ArrayList<Poll>();

    @OneToMany()
    public final List<Vote> votes = new ArrayList<Vote>();

    public PollUser() {

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toJson() {
        Gson gson = new Gson();
        String representation = gson.toJson(this);
        return representation;
    }

    public void addPoll(Poll poll) {
        polls.add(poll);
    }

    public void removePoll(Poll p) {
        polls.remove(p);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public List<Poll> getPolls() { return polls; }

//    public List<Vote> getVotes() {
//        return votes;
//    }
}
