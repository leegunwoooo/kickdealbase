package ang.gimozzi.kickdealbase.domain;

public enum Role {
    ADMIN,
    USER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}