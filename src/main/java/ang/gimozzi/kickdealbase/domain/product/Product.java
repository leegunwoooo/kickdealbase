package ang.gimozzi.kickdealbase.domain.product;

import ang.gimozzi.kickdealbase.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    private User seller;

    private String imageUrl;

    @Builder
    public Product(String name, String description, User seller, String imageUrl) {
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.imageUrl = imageUrl;
    }

    public void update(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public void validUser(User user){
        if(this.seller != user){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
    }

}
