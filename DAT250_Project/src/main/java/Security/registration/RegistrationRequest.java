package Security.registration;

public class RegistrationRequest {

    private final String username;
    private final String password;

    public RegistrationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

}
