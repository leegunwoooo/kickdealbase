package ang.gimozzi.kickdealbase.presentation.message;

import ang.gimozzi.kickdealbase.domain.chatroom.Message;

import java.time.LocalDateTime;

public record MessageResponse(
        Long id,
        Long roomId,
        Long senderId,
        String senderName,
        String content,
        String type,
        LocalDateTime timestamp
) {
    public static MessageResponse from(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getRoomId(),
                message.getSenderId(),
                message.getSenderName(),
                message.getContent(),
                message.getType().name(),
                message.getTimestamp()
        );
    }
}
