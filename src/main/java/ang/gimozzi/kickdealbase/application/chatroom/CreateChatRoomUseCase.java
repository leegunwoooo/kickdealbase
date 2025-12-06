package ang.gimozzi.kickdealbase.application.chatroom;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;
import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.product.service.ProductFacade;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.domain.user.service.UserFacade;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ChatRoomRepository;
import ang.gimozzi.kickdealbase.presentation.chatroom.dto.response.ChatRoomResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.security.InvalidParameterException;

@UseCase
@RequiredArgsConstructor
public class CreateChatRoomUseCase {

    private final ChatRoomRepository chatRoomRepository;
    private final UserFacade userFacade;
    private final ProductFacade productFacade;

    @Transactional
    public ChatRoomResponse createRoom(User buyer, Long productId){
        Product product = productFacade.getProduct(productId);

        User seller = userFacade.getUser(product.getSellerId());

        validate(product, buyer.getId());

        return ChatRoomResponse.from(
                chatRoomRepository.findByBuyerAndSellerAndProductId(buyer, seller, productId)
                        .orElseGet(() -> chatRoomRepository.save(
                                ChatRoom.builder()
                                        .buyer(buyer)
                                        .seller(seller)
                                        .productId(productId)
                                        .name(product.getName())
                                        .price(product.getPrice())
                                        .build()
                        ))
        );
    }

    public void validate(Product product, Long userId){
        if(product.getSellerId().equals(userId)){
            throw new InvalidParameterException("자기 자신과 대화할려 들지 마십시오");
        }
    }
}
