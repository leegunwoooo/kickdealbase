package ang.gimozzi.kickdealbase.presentation.message;

import ang.gimozzi.kickdealbase.application.message.GetMessageUseCase;
import ang.gimozzi.kickdealbase.presentation.message.dto.response.ListSimpleMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{room-id}/messages")
public class MessageRestController {

    private final GetMessageUseCase getMessageUseCase;

    public ListSimpleMessageResponse listSimpleMessage(
            @PathVariable(value = "room-id") Long roomId
    ) {
        return getMessageUseCase.findMessage(roomId);
    }
}
