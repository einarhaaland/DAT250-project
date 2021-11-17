/*package Security.registration;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
public class Registration {

    private final RegistrationService registrationService;

    public Registration(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}*/
