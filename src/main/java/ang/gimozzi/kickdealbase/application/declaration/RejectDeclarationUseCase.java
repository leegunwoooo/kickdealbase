package ang.gimozzi.kickdealbase.application.declaration;

import ang.gimozzi.kickdealbase.domain.declaration.Declaration;
import ang.gimozzi.kickdealbase.domain.declaration.service.DeclarationFacade;
import ang.gimozzi.kickdealbase.presentation.declaration.dto.response.DeclarationResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RejectDeclarationUseCase {

    private final DeclarationFacade declarationFacade;

    public DeclarationResponse rejectDeclaration(Long id) {
        Declaration declaration = declarationFacade.findById(id);

        declaration.rejectDeclaration();

        return DeclarationResponse.from(declaration);
    }
}
