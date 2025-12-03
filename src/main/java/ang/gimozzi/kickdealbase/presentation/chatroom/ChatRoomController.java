package ang.gimozzi.kickdealbase.presentation.chatroom;

import ang.gimozzi.kickdealbase.application.chatroom.CreateChatRoomUseCase;
import ang.gimozzi.kickdealbase.application.chatroom.GetAllChatRoomUseCase;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.chatroom.dto.response.ChatRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final CreateChatRoomUseCase createChatRoomUseCase;
    private final GetAllChatRoomUseCase getAllChatRoomUseCase;

    @PostMapping("/{product-id}")
    public ChatRoomResponse createChatRoom(
            @AuthenticationPrincipal User buyer,
            @PathVariable(value = "product-id") Long productId
    ){
        return createChatRoomUseCase.createRoom(buyer, productId);
    }

    @GetMapping
    public List<ChatRoomResponse> getChatRooms(
            @AuthenticationPrincipal User user
    ){
        return getAllChatRoomUseCase.getChatRoomsByUser(user);
    }

}
