package ang.gimozzi.kickdealbase.domain.message.service;

import ang.gimozzi.kickdealbase.domain.message.Message;
import ang.gimozzi.kickdealbase.infrastructure.persistence.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageFacade {

    private final MessageRepository messageRepository;

    public Message getMessage(Long messageId){
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메시지"));
    }

}
