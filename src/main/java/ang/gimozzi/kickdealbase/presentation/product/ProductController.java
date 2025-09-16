package ang.gimozzi.kickdealbase.presentation.product;

import ang.gimozzi.kickdealbase.application.product.*;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductRequest;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController("/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final ViewAllProductUseCase viewAllProductUseCase;
    private final ViewOneProductUseCase viewOneProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProduct(
            @RequestPart("product") ProductRequest productRequest,
            @RequestPart("image") MultipartFile image,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(createProductUseCase.createProduct(productRequest, image, user));
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @RequestPart("product") ProductRequest productRequest,
            @RequestPart("image") MultipartFile image,
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ){
        return ResponseEntity.ok(updateProductUseCase.updateProduct(productRequest, image, user, id));
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return viewAllProductUseCase.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(viewOneProductUseCase.getOneProduct(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(deleteProductUseCase.deleteProduct(id, user));
    }

}
