package ang.gimozzi.kickdealbase.presentation.product.dto;

import ang.gimozzi.kickdealbase.domain.product.Category;
import ang.gimozzi.kickdealbase.domain.product.Product;
import lombok.Getter;

@Getter
public class ProductResponse {

    private final Long id;

    private final String name;

    private final String description;

    private final Category category;

    private final Integer price;

    private final String seller;

    private final String url;

    public ProductResponse(Product product, String url) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.seller = product.getSeller().getUsername();
        this.url = url;
    }
}
