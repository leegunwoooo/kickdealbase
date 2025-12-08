package ang.gimozzi.kickdealbase.presentation.message;

import ang.gimozzi.kickdealbase.application.message.GetMessageUseCase;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.message.dto.response.ListSimpleMessageResponse;
import ang.gimozzi.kickdealbase.shared.annotation.ValidUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{room-id}/messages")
public class MessageRestController {

    private final GetMessageUseCase getMessageUseCase;

    @GetMapping
    @ValidUser
    public ListSimpleMessageResponse listSimpleMessage(
            @PathVariable(value = "room-id") Long roomId,
            @AuthenticationPrincipal User user
    ) {
        return getMessageUseCase.findMessage(roomId);
    }
}
