package ang.gimozzi.kickdealbase.application.point;

import ang.gimozzi.kickdealbase.domain.apply.Apply;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.domain.user.service.UserFacade;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ApplyRepository;
import ang.gimozzi.kickdealbase.presentation.apply.dto.request.ApplyRequest;
import ang.gimozzi.kickdealbase.presentation.apply.dto.response.ApplyResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class ApplyChargePointUseCase {

    private final ApplyRepository applyRepository;
    private final UserFacade userFacade;

    @Transactional
    public ApplyResponse apply(ApplyRequest request, User user) {
        User sender = userFacade.findByEmail(user.getEmail());
        return ApplyResponse.from(
                applyRepository.save(
                        Apply.builder()
                                .user(sender)
                                .point(request.getPoint())
                                .build()
                )
        );
    }

}
