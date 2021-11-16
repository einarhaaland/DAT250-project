package Security.registration;

import Model.PollUser;
import Security.AppUserService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final AppUserService appUserService;

    public RegistrationService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    public String register (RegistrationRequest request) {
        return appUserService.signUpUser(
                new PollUser(
                        request.getUsername(),
                        request.getPassword()
                )
        );
    }
}
