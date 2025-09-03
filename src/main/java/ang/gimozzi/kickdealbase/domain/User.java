package ang.gimozzi.kickdealbase.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Builder
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }
}
