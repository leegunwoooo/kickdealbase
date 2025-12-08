package ang.gimozzi.kickdealbase.presentation.apply.dto.response;

import ang.gimozzi.kickdealbase.domain.apply.Apply;
import ang.gimozzi.kickdealbase.domain.apply.ApplyStatus;

public record ApplyResponse(
        Long id,
        String user,
        Integer point,
        ApplyStatus status
) {
    public static ApplyResponse from(Apply apply) {
        return new ApplyResponse(
                apply.getId(),
                apply.getUserName(),
                apply.getPoint(),
                apply.getStatus()
        );
    }
}
