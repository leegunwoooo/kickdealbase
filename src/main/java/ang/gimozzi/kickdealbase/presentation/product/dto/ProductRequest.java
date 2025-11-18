package ang.gimozzi.kickdealbase.presentation.product.dto;

import ang.gimozzi.kickdealbase.domain.product.Category;
import lombok.Getter;

@Getter
public class ProductRequest {

    private String name;

    private String description;

    private Category category;

    private Integer price;

}
