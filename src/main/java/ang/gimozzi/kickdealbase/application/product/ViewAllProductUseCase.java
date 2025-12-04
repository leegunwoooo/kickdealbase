package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.infrastructure.s3.S3Service;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ViewAllProductUseCase {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return productRepository.findAll(pageable)
                .map(p -> new ProductResponse(p, s3Service.generateFileUrl(p.getImageUrl())));
    }

}
