package ang.gimozzi.kickdealbase.domain.chatroom;

import ang.gimozzi.kickdealbase.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_chatroom")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User buyer;

    @ManyToOne
    private User seller;

    private Long productId;

    @Builder
    public ChatRoom(User buyer, User seller, Long productId) {
        this.buyer = buyer;
        this.seller = seller;
        this.productId = productId;
    }

    public String getSellerName() {
        return seller.getUsername();
    }

    public String getBuyerName() {
        return buyer.getUsername();
    }

}

