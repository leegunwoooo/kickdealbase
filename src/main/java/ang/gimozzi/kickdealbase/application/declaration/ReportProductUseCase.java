package ang.gimozzi.kickdealbase.application.declaration;

import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.product.service.ProductFacade;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.declaration.dto.request.DeclarationRequest;
import ang.gimozzi.kickdealbase.presentation.declaration.dto.response.DeclarationResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ReportProductUseCase {

    private final DeclarationFixture declarationFixture;
    private final ProductFacade productFacade;

    @Transactional
    public DeclarationResponse reportProduct(Long id, DeclarationRequest declarationRequest) {
        Product product = productFacade.getProduct(id);

        User badMan = product.getSeller();

        return DeclarationResponse.from(
                declarationFixture.reportProduct(badMan, product, declarationRequest.getReason())
        );

    }
}
