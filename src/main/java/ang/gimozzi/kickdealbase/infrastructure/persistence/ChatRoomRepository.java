package ang.gimozzi.kickdealbase.infrastructure.persistence;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;
import ang.gimozzi.kickdealbase.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findDistinctByMessagesIsNotEmptyAndBuyerOrSeller(User buyer, User seller);
    List<ChatRoom> findByBuyerOrSeller(User buyer, User seller);
    Optional<ChatRoom> findByBuyerAndSellerAndProductId(User buyer, User seller, Long productId);
}
