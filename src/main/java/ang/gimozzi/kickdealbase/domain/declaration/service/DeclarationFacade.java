package ang.gimozzi.kickdealbase.domain.declaration.service;

import ang.gimozzi.kickdealbase.domain.declaration.Declaration;
import ang.gimozzi.kickdealbase.infrastructure.persistence.DeclarationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeclarationFacade {

    private final DeclarationRepository declarationRepository;

    public Declaration findById(Long id){
        return declarationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("신고없음"));
    }
}
