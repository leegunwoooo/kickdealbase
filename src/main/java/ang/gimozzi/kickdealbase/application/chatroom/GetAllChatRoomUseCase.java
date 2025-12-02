package ang.gimozzi.kickdealbase.application.chatroom;

import ang.gimozzi.kickdealbase.domain.chatroom.service.ChatRoomFacade;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.chatroom.dto.response.ChatRoomResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class GetAllChatRoomUseCase {

    private final ChatRoomFacade chatRoomFacade;

    public List<ChatRoomResponse> getChatRoomsByUser(User user){
        return chatRoomFacade.getChatRoomsByUser(user)
                .stream()
                .map(ChatRoomResponse::from)
                .toList();
    }
}
