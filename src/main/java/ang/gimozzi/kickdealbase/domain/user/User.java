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

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private Integer point = 0;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Integer opportunity = 2;

    @Builder
    public User(String email, String username, String password, Role role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    public void decreaseOpportunity() {
        if (this.opportunity <= 0) {
            throw new IllegalStateException("더 이상 기회가 없습니다.");
        }
        this.opportunity--;

        if (this.opportunity == 0) {
            ban();
        }
    }

    public void ban() {
        this.role = Role.BANNED;
    }

    public void calculatePoint(Integer point) {
        this.point += point;
    }

}
