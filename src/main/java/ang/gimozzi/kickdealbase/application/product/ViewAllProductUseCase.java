package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.infrastructure.s3.S3Service;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ViewAllProductUseCase {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getId).reversed())
                .map(p -> new ProductResponse(p, s3Service.generateFileUrl(p.getImageUrl())))
                .toList();
    }

}
