package ang.gimozzi.kickdealbase.presentation.chatroom.dto.response;

import ang.gimozzi.kickdealbase.domain.chatroom.ChatRoom;

public record ChatRoomResponse(
        Long id,
        String name
) {
    public static ChatRoomResponse from(ChatRoom chatRoom){
        return new  ChatRoomResponse(chatRoom.getId(), chatRoom.getName());
    }
}
