package ang.gimozzi.kickdealbase.presentation.message.dto.response;

import ang.gimozzi.kickdealbase.domain.message.Message;
import ang.gimozzi.kickdealbase.domain.user.User;

import java.time.LocalDateTime;

public record SimpleMessageResponse(
        Long id,
        String content,
        User sender,
        LocalDateTime timeStamp
) {
    public static SimpleMessageResponse from(Message message) {
        return new SimpleMessageResponse(message.getId(), message.getContent(), message.getSender(), message.getTimestamp());
    }
}
