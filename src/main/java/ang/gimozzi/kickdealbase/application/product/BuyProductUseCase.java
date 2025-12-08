package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.product.service.ProductFacade;
import ang.gimozzi.kickdealbase.domain.user.User;

import ang.gimozzi.kickdealbase.domain.user.service.UserFacade;
import ang.gimozzi.kickdealbase.infrastructure.s3.S3Service;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class BuyProductUseCase {

    private final ProductFacade productFacade;
    private final UserFacade userFacade;
    private final S3Service s3Service;

    @Transactional
    public ProductResponse executeBuy(Long productId, User user) {
        Product product = productFacade.getProduct(productId);

        User buyer = userFacade.getUser(user.getId());

        product.validatePurchaseBy(buyer);
        product.purchaseBy(buyer);

        String imageUrl = s3Service.generateFileUrl(product.getImageUrl());
        return new ProductResponse(product, imageUrl);
    }
}
