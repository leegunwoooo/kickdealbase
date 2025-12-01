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

    private String name;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);

    }
    public void removeUser(User user) {
        users.remove(user);
    }

    @Builder
    public ChatRoom(String name, Set<User> users){
        this.name = name;
        this.users = users;
    }

}

