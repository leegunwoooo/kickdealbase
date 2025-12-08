package ang.gimozzi.kickdealbase.domain.declaration;

import ang.gimozzi.kickdealbase.domain.message.Message;
import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_declaration")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Declaration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    private Product product;

    private String reason;

    @Builder(builderMethodName = "messageDeclarationBuilder")
    public Declaration(User user, Message message, String reason){
        this.user = user;
        this.message = message;
        this.reason = reason;
    }

    @Builder(builderMethodName = "productDeclarationBuilder")
    public Declaration(User user, Product product, String reason){
        this.user = user;
        this.product = product;
        this.reason = reason;
    }

    public void decreaseOpportunity() {
        this.user.decreaseOpportunity();
    }

}
