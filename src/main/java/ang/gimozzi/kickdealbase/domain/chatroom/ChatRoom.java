package ang.gimozzi.kickdealbase.domain.chatroom;

import ang.gimozzi.kickdealbase.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_chatroom")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    private String name;

    @Builder
    public ChatRoom(User user1, User user2, String name) {
        this.user1 = user1;
        this.user2 = user2;
        this.name = name;
    }

}

