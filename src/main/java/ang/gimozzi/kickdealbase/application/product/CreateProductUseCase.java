package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ProductRepository;
import ang.gimozzi.kickdealbase.infrastructure.s3.S3Service;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductRequest;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@UseCase
@RequiredArgsConstructor
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    public ProductResponse createProduct(ProductRequest request, MultipartFile image, User user){
        String url = s3Service.uploadFile(image);

        return new ProductResponse(productRepository.save(
                Product.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .seller(user)
                        .imageUrl(url)
                        .build()
        ));
    }

}
