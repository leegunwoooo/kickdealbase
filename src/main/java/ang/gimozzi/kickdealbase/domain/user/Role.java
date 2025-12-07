package ang.gimozzi.kickdealbase.domain.user;

public enum Role {
    ADMIN,
    USER,
    BANNED;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}