package ang.gimozzi.kickdealbase.domain.chatroom.service;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class ChatRoomFacade {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom getChatRoom(Long chatRoomId){
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방"));
    }

    public List<ChatRoom> getChatRoomsByUser(User user){
        return chatRoomRepository.findByBuyerOrSeller(user, user);
    }
}
