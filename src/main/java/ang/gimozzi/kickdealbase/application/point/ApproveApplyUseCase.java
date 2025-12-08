package ang.gimozzi.kickdealbase.application.point;

import ang.gimozzi.kickdealbase.domain.apply.Apply;
import ang.gimozzi.kickdealbase.domain.apply.service.ApplyFacade;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ApplyRepository;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ApproveApplyUseCase {

    private final ApplyFacade applyFacade;
    private final ApplyRepository applyRepository;

    @Transactional
    public void apply(Long id){
        Apply apply = applyFacade.findById(id);

        User user = apply.getUser();

        user.calculatePoint(apply.getPoint());

        applyRepository.delete(apply);
    }
}
