package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Category;
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
public class ViewProductByCategory {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public Page<ProductResponse> viewProductByCategory(Category category, Integer page) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("id").descending());
        return productRepository.findProductsByCategory(category, pageable)
                .map(p -> new ProductResponse(p, s3Service.generateFileUrl(p.getImageUrl())));
    }
}
