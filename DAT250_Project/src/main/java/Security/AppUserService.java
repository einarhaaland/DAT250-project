package Security;

import Controller.JpaPollUserDao;
import Model.PollUser;
import Security.token.ConfirmationToken;
import Security.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AppUserService {

    private final JpaPollUserDao userService;
    private final PasswordEncoder encoder;
    private final ConfirmationTokenService confirmationTokenService;

    public AppUserService(JpaPollUserDao userService, PasswordEncoder encoder, ConfirmationTokenService confirmationTokenService) {
        this.userService = userService;
        this.encoder = encoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public PollUser loadUserByUsername2(String s) throws UsernameNotFoundException {

        try {
            return userService.findByUsername(s);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User don't exist");
        }
    }

    public PollUser signUpUser(PollUser user) {

        List<PollUser> userExists = userService.getAll();

        for (PollUser p : userExists) {
            if (user.equals(p)) throw new IllegalStateException("Username already exists");
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

        return user;
    }
}
