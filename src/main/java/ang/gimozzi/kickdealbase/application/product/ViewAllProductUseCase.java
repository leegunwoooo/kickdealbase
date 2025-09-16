package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ViewAllProductUseCase {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)
                .toList();
    }

}
