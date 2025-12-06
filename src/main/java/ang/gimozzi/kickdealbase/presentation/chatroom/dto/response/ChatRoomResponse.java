package ang.gimozzi.kickdealbase.presentation.chatroom.dto.response;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;

public record ChatRoomResponse(
        Long id,
        Long productId,
        String name,
        String buyer,
        String seller,
        Integer price
) {
    public static ChatRoomResponse from(ChatRoom chatRoom){
        return new  ChatRoomResponse(chatRoom.getId(), chatRoom.getProductId(), chatRoom.getName(), chatRoom.getBuyerName(), chatRoom.getSellerName(), chatRoom.getPrice());
    }
}
