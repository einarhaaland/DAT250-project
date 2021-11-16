package Security.token;

import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken (ConfirmationToken confirmationToken) {

        confirmationTokenRepository.save(confirmationToken);
    }
}
