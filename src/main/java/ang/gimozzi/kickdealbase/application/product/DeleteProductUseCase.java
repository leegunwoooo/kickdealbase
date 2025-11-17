package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.product.service.ProductFacade;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.infrastructure.s3.S3Service;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteProductUseCase {

    private final ProductFacade productFacade;
    private final ProductRepository productRepository;
    private final S3Service s3Service;

    @Transactional
    public void deleteProduct(Long productId, User user){

        Product product = productFacade.getProduct(productId);

        product.validUser(user);

        s3Service.deleteFile(product.getImageUrl());

        productRepository.delete(product);

    }

}
