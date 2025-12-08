package ang.gimozzi.kickdealbase.presentation.message;

import ang.gimozzi.kickdealbase.application.message.SendMessageUseCase;
import ang.gimozzi.kickdealbase.infrastructure.websocket.UserPrincipal;
import ang.gimozzi.kickdealbase.presentation.message.dto.request.MessageRequest;
import ang.gimozzi.kickdealbase.presentation.message.dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SendMessageUseCase sendMessageUseCase;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{roomId}/send")
    public void sendMessage(@DestinationVariable Long roomId,
                            MessageRequest messageRequest,
                            SimpMessageHeaderAccessor headerAccessor) {

        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) headerAccessor.getUser();
        UserPrincipal user = (UserPrincipal) auth.getPrincipal();

        MessageResponse response = sendMessageUseCase.sendMessage(roomId, user, messageRequest);
        messagingTemplate.convertAndSend("/topic/" + roomId, response);
    }
}
