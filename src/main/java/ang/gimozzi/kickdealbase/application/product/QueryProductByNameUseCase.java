package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class QueryProductByNameUseCase {

    private final ProductRepository productRepository;

    public List<ProductResponse> getProductsByName(String keyword) {
        List<Product> products = productRepository.findProductsByNameContaining(keyword);

        return products
                .stream()
                .map(ProductResponse::new)
                .toList();

    }
}
