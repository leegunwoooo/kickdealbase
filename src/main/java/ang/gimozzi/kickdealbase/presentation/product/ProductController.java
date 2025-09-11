package ang.gimozzi.kickdealbase.presentation.product;

import ang.gimozzi.kickdealbase.application.product.CreateProductUseCase;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductRequest;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController("/product")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(
            @RequestPart("product") ProductRequest productRequest,
            @RequestPart("image") MultipartFile image,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(createProductUseCase.createProduct(productRequest, image, user));
    }

}
