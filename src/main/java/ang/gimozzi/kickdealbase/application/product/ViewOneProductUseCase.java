package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.product.service.ProductFacade;
import ang.gimozzi.kickdealbase.infrastructure.s3.S3Service;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ViewOneProductUseCase {

    private final ProductFacade productFacade;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public ProductResponse getOneProduct(Long productId) {
        Product product = productFacade.getProduct(productId);

        String url = s3Service.generateFileUrl(product.getImageUrl());

        return new ProductResponse(product, url);
    }

}
