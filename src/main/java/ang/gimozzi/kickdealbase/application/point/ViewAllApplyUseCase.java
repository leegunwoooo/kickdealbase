package ang.gimozzi.kickdealbase.application.point;

import ang.gimozzi.kickdealbase.infrastructure.persistence.ApplyRepository;
import ang.gimozzi.kickdealbase.presentation.apply.dto.response.ApplyListResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;


@UseCase
@RequiredArgsConstructor
public class ViewAllApplyUseCase {

    private final ApplyRepository applyRepository;

    public ApplyListResponse viewAllApply() {
        return ApplyListResponse.from(applyRepository.findAll());
    }
}
