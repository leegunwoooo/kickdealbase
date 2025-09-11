package ang.gimozzi.kickdealbase.presentation.product.dto;

import ang.gimozzi.kickdealbase.domain.product.Product;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;

    private final String name;

    private final String description;

    private final String seller;

    private final String url;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.seller = product.getSeller().getUsername();
        this.url = product.getImageUrl();
    }
}
