package Repositories;

import Model.PollUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PollUserRepository extends JpaRepository<PollUser, Integer> {

    Optional<PollUser> findByUsername(String username);
}
