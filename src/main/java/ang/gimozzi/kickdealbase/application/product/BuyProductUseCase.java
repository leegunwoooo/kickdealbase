package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.product.service.ProductFacade;
import ang.gimozzi.kickdealbase.domain.user.User;

import ang.gimozzi.kickdealbase.infrastructure.s3.S3Service;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class BuyProductUseCase {

    private final ProductFacade productFacade;
    private final S3Service s3Service;

    @Transactional
    public ProductResponse buyProduct(Long id, User user) {
        Product product = productFacade.getProduct(id);

        product.sold(user);

        return new ProductResponse(product, s3Service.generateFileUrl(product.getImageUrl()));
    }

}
