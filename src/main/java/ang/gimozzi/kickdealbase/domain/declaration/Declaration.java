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

    @Enumerated(EnumType.STRING)
    private DeclarationStatus status = DeclarationStatus.PROCESSING;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    private Product product;

    private String reason;

    public Declaration(User user, Message message, String reason){
        this.user = user;
        this.message = message;
        this.reason = reason;
        this.type = Type.MESSAGE;
    }

    public Declaration(User user, Product product, String reason){
        this.user = user;
        this.product = product;
        this.reason = reason;
        this.type = Type.PRODUCT;
    }

    public void decreaseOpportunity() {
        this.user.decreaseOpportunity();
    }

    public String getMessageContent(){
        return this.message.getContent();
    }

    public String getProductName(){
        return this.product.getName();
    }

    public void approveDeclaration() {
        this.status = DeclarationStatus.COMPLETED;
        user.decreaseOpportunity();
    }

    public void rejectDeclaration() {
        this.status = DeclarationStatus.REJECT;
    }

}
