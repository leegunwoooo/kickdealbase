package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.infrastructure.s3.S3Service;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UseCase
@RequiredArgsConstructor
public class ViewMyProductUseCase {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    public Page<ProductResponse> findAllMyProducts(User user, Integer page) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("id").descending());
        Long id = user.getId();

        return productRepository.findAllBySellerIdOrBuyerId(id, id, pageable)
                .map(p -> new ProductResponse(p, s3Service.generateFileUrl(p.getImageUrl())));
    }
}
