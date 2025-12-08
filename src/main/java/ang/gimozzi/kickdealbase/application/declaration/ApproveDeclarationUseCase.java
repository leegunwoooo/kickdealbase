package ang.gimozzi.kickdealbase.application.declaration;

import ang.gimozzi.kickdealbase.domain.declaration.Declaration;
import ang.gimozzi.kickdealbase.domain.declaration.service.DeclarationFacade;
import ang.gimozzi.kickdealbase.presentation.declaration.dto.response.DeclarationResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ApproveDeclarationUseCase {

    private final DeclarationFacade declarationFacade;

    @Transactional
    public DeclarationResponse approveDeclaration(Long id) {
        Declaration declaration = declarationFacade.findById(id);

        declaration.approveDeclaration();

        return DeclarationResponse.from(declaration);
    }

}
