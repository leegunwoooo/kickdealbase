package ang.gimozzi.kickdealbase.application.point;

import ang.gimozzi.kickdealbase.domain.apply.Apply;
import ang.gimozzi.kickdealbase.domain.apply.service.ApplyFacade;
import ang.gimozzi.kickdealbase.presentation.apply.dto.response.ApplyResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class RejectApplyUseCase {

    private final ApplyFacade applyFacade;

    @Transactional
    public ApplyResponse reject(Long id) {
        Apply apply = applyFacade.findById(id);

        apply.reject();

        return ApplyResponse.from(apply);
    }

}
