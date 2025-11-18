package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Category;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ViewProductByCategory {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> viewProductByCategory(Category category) {
        return productRepository.findProductsByCategory(category)
                .stream()
                .map(ProductResponse::new)
                .toList();
    }
}
