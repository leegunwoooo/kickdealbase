package ang.gimozzi.kickdealbase.presentation.message;

import ang.gimozzi.kickdealbase.application.message.SendMessageUseCase;
import ang.gimozzi.kickdealbase.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SendMessageUseCase sendMessageUseCase;

    @MessageMapping("/chat/{roomId}/send")
    @SendTo("/topic/{roomId}")
    public MessageResponse sendMessage(
            @DestinationVariable Long roomId,
            @AuthenticationPrincipal User user,
            MessageRequest messageRequest
    ){
        return sendMessageUseCase.sendMessage(roomId, user, messageRequest);
    }

}
