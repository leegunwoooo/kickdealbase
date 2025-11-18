package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class QueryProductByNameUseCase {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByName(String keyword) {
        return productRepository.findProductsByNameContaining(keyword)
                .stream()
                .map(ProductResponse::new)
                .toList();

    }
}
