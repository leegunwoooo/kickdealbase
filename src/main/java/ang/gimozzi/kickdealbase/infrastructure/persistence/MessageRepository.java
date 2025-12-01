package ang.gimozzi.kickdealbase.infrastructure.persistence;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;
import ang.gimozzi.kickdealbase.domain.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoom(ChatRoom chatRoom);
}
