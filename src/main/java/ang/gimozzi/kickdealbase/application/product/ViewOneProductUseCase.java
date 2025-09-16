package ang.gimozzi.kickdealbase.application.product;

import ang.gimozzi.kickdealbase.domain.product.service.ProductFacade;
import ang.gimozzi.kickdealbase.presentation.product.dto.ProductResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ViewOneProductUseCase {

    private final ProductFacade productFacade;

    @Transactional(readOnly = true)
    public ProductResponse getOneProduct(Long productId) {
        return new ProductResponse(productFacade.getProduct(productId));
    }

}
