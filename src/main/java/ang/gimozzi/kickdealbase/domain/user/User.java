package ang.gimozzi.kickdealbase.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private Role role;

    @Builder
    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

}
