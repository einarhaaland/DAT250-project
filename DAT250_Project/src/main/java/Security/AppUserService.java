package Security;

import Controller.JpaPollUserDao;
import Model.PollUser;
import Repositories.PollUserRepository;
import Security.token.ConfirmationToken;
import Security.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {

    private final PollUserRepository pollUserRepository;
    private final JpaPollUserDao userService;
    private final PasswordEncoder encoder;
    private final ConfirmationTokenService confirmationTokenService;

    public AppUserService(PollUserRepository pollUserRepository, JpaPollUserDao userService, PasswordEncoder encoder, ConfirmationTokenService confirmationTokenService) {
        this.pollUserRepository = pollUserRepository;
        this.userService = userService;
        this.encoder = encoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        try {
            return userService.findByUsername(s);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User don't exist");
        }
    }

    public String signUpUser(PollUser user) {
        PollUser userExists = userService.findByUsername(user.getUsername());

        if (userExists.getId() > 0) {
            throw new IllegalStateException("username already exists");
        }

        String password = encoder.encode(user.getPassword());

        user.setPassword(password);

        userService.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }
}
