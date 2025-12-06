package ang.gimozzi.kickdealbase.application.chatroom;

import ang.gimozzi.kickdealbase.domain.chatroom.service.ChatRoomFacade;
import ang.gimozzi.kickdealbase.presentation.chatroom.dto.response.ChatRoomResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class GetChatRoomInfoUseCase {

    private final ChatRoomFacade chatRoomFacade;

    public ChatRoomResponse getChatRoomInfo(Long chatRoomId) {
        return ChatRoomResponse.from(chatRoomFacade.getChatRoom(chatRoomId));
    }
}
