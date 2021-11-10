package Security;

public enum AppUserPermission {
    POLLUSER_READ("polluser:read"),
    POLLUSER_WRITE("polluser:write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
