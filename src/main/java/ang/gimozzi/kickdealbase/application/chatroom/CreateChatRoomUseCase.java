package ang.gimozzi.kickdealbase.application.chatroom;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;
import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.product.service.ProductFacade;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.domain.user.service.UserFacade;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ChatRoomRepository;
import ang.gimozzi.kickdealbase.presentation.chatroom.dto.response.ChatRoomResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

import java.security.InvalidParameterException;

@UseCase
@RequiredArgsConstructor
public class CreateChatRoomUseCase {

    private final ChatRoomRepository chatRoomRepository;
    private final UserFacade userFacade;
    private final ProductFacade productFacade;

    public ChatRoomResponse createRoom(User buyer, Long productId){
        Product product = productFacade.getProduct(productId);

        User seller = userFacade.getUser(product.getSellerId());

        validate(product, seller.getId());

        return ChatRoomResponse.from(
                chatRoomRepository.save(
                        ChatRoom.builder()
                                .name(product.getName())
                                .buyer(buyer)
                                .seller(seller)
                                .build()
                )
        );
    }

    public void validate(Product product, Long userId){
        if(!product.getSellerId().equals(userId)){
            throw new InvalidParameterException("자기 자신과 대화할려 들지 마십시오");
        }
    }
}
