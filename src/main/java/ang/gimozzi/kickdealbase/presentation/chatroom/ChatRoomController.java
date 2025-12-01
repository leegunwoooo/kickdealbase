package ang.gimozzi.kickdealbase.presentation.chatroom;

import ang.gimozzi.kickdealbase.application.chatroom.CreateChatRoomUseCase;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.chatroom.dto.response.ChatRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{product-id}/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final CreateChatRoomUseCase createChatRoomUseCase;

    @PostMapping
    public ChatRoomResponse createChatRoom(
            @AuthenticationPrincipal User user,
            @PathVariable(value = "product-id") Long productId
    ){
        return createChatRoomUseCase.createRoom(user, productId);
    }

}
