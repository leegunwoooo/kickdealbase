package ang.gimozzi.kickdealbase.application.message;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;
import ang.gimozzi.kickdealbase.domain.chatroom.service.ChatRoomFacade;
import ang.gimozzi.kickdealbase.domain.message.Message;
import ang.gimozzi.kickdealbase.domain.message.MessageType;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.domain.user.service.UserFacade;
import ang.gimozzi.kickdealbase.infrastructure.persistence.MessageRepository;
import ang.gimozzi.kickdealbase.infrastructure.websocket.UserPrincipal;
import ang.gimozzi.kickdealbase.presentation.message.dto.request.MessageRequest;
import ang.gimozzi.kickdealbase.presentation.message.dto.response.MessageResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SendMessageUseCase {

    private final MessageRepository messageRepository;
    private final ChatRoomFacade chatRoomFacade;
    private final UserFacade userFacade;

    public MessageResponse sendMessage(Long chatRoomId, UserPrincipal sender, MessageRequest request) {
        ChatRoom chatRoom = chatRoomFacade.getChatRoom(chatRoomId);
        User user = userFacade.getUser(sender.id());

        return MessageResponse.from(
                messageRepository.save(
                        Message.builder()
                                .room(chatRoom)
                                .sender(user)
                                .content(request.getContent())
                                .type(MessageType.CHAT)
                                .build()
                )
        );
    }
}
