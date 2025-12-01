package ang.gimozzi.kickdealbase.application.message;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;
import ang.gimozzi.kickdealbase.domain.chatroom.service.ChatRoomFacade;
import ang.gimozzi.kickdealbase.domain.message.Message;
import ang.gimozzi.kickdealbase.domain.message.MessageType;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.domain.user.service.UserFacade;
import ang.gimozzi.kickdealbase.infrastructure.persistence.MessageRepository;
import ang.gimozzi.kickdealbase.presentation.message.MessageRequest;
import ang.gimozzi.kickdealbase.presentation.message.MessageResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class SendMessageUseCase {

    private final MessageRepository messageRepository;
    private final ChatRoomFacade chatRoomFacade;
    private final UserFacade userFacade;

    public MessageResponse sendMessage(Long chatRoomId, User sender, MessageRequest request) {
        ChatRoom chatRoom = chatRoomFacade.getChatRoom(chatRoomId);

        return MessageResponse.from(
                messageRepository.save(
                        Message.builder()
                                .room(chatRoom)
                                .sender(sender)
                                .content(request.getContent())
                                .type(MessageType.CHAT)
                                .build()
                )
        );
    }
}
