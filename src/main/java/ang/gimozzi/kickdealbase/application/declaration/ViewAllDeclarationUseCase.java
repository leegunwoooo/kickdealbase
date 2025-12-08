package ang.gimozzi.kickdealbase.application.declaration;

import ang.gimozzi.kickdealbase.infrastructure.persistence.DeclarationRepository;
import ang.gimozzi.kickdealbase.presentation.declaration.dto.response.DeclarationListResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ViewAllDeclarationUseCase {

    private final DeclarationRepository declarationRepository;

    @Transactional
    public DeclarationListResponse getAllDeclarations(){
        return DeclarationListResponse.from(declarationRepository.findAll());
    }

}
