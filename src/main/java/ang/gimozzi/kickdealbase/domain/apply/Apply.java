package ang.gimozzi.kickdealbase.domain.apply;

import ang.gimozzi.kickdealbase.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_apply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Integer point;

    @Enumerated(EnumType.STRING)
    private ApplyStatus status = ApplyStatus.WAITING;

    @Builder
    public Apply(User user, Integer point) {
        this.user = user;
        this.point = point;
    }

    public void reject(){
        this.status = ApplyStatus.REJECT;
    }

    public String getUserName(){
        return this.user.getUsername();
    }

}
