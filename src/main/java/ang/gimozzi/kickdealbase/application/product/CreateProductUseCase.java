package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.Product;
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
public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    @Transactional
    public ProductResponse createProduct(ProductRequest request, MultipartFile image, User user){
        String fileName = s3Service.uploadFile(image);
        String url = s3Service.generateFileUrl(fileName);

        Product product = productRepository.save(
                Product.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .category(request.getCategory())
                        .price(request.getPrice())
                        .seller(user)
                        .imageUrl(url)
                        .build()
        );

        return new ProductResponse(product, url);
    }

}
