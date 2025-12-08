package ang.gimozzi.kickdealbase.presentation.product;

import ang.gimozzi.kickdealbase.application.product.*;
import ang.gimozzi.kickdealbase.domain.product.Category;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductRequest;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final ViewAllProductUseCase viewAllProductUseCase;
    private final ViewOneProductUseCase viewOneProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ViewProductByCategory viewProductByCategoryUseCase;
    private final QueryProductByNameUseCase queryProductByNameUseCase;
    private final BuyProductUseCase buyProductUseCase;

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
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ){
        return ResponseEntity.ok(updateProductUseCase.updateProduct(productRequest, image, user, id));
    }

    @GetMapping
    public Page<ProductResponse> getAllProducts(
            @RequestParam(defaultValue = "0") Integer page
    ) {
        return viewAllProductUseCase.getAllProducts(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(viewOneProductUseCase.getOneProduct(id));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        deleteProductUseCase.deleteProduct(id, user);
    }

    @GetMapping("/categories")
    public Page<ProductResponse> getProductsByCategory(
            @RequestParam(name = "category") Category category,
            @RequestParam(defaultValue = "0") Integer page
    ){
        return viewProductByCategoryUseCase.viewProductByCategory(category, page);
    }

    @GetMapping("/query")
    public Page<ProductResponse> getProductsByQuery(
            @RequestParam(name = "query") String keyword,
            @RequestParam(defaultValue = "0") Integer page
    ){
        return queryProductByNameUseCase.getProductsByName(keyword, page);
    }

    @PostMapping("/{id}/buy")
    public ProductResponse buyProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return buyProductUseCase.buyProduct(id, user);
    }

}
