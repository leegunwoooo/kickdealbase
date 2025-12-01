package ang.gimozzi.kickdealbase.application.message;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;
import ang.gimozzi.kickdealbase.domain.chatroom.service.ChatRoomFacade;
import ang.gimozzi.kickdealbase.domain.message.Message;
import ang.gimozzi.kickdealbase.infrastructure.persistence.MessageRepository;
import ang.gimozzi.kickdealbase.presentation.message.ListSimpleMessageResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@UseCase
public class GetMessageUseCase {

    private final ChatRoomFacade chatRoomFacade;
    private final MessageRepository messageRepository;

    public ListSimpleMessageResponse findMessage(Long roomId) {
        ChatRoom chatroom = chatRoomFacade.getChatRoom(roomId);

        List<Message> messages = messageRepository.findByRoom(chatroom);

        return ListSimpleMessageResponse.from(messages);
    }

}
