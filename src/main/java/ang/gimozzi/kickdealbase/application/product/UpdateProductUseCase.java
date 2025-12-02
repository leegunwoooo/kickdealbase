package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.product.service.ProductFacade;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.infrastructure.s3.S3Service;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductRequest;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@UseCase
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final ProductRepository productRepository;
    private final S3Service s3Service;
    private final ProductFacade productFacade;

    @Transactional
    public ProductResponse updateProduct(ProductRequest request, MultipartFile image, User user, Long productId) {

        Product product = productFacade.getProduct(productId);

        product.validUser(user);

        String url = product.getImageUrl();

        if (image != null && !image.isEmpty()) {
            url = s3Service.uploadFile(image);
        }

        product.update(request.getName(), request.getDescription(), request.getCategory(), request.getPrice(), url);

        url = s3Service.generateFileUrl(url);

        return new ProductResponse(product, url);

    }

}
