package ang.gimozzi.kickdealbase.presentation.apply.dto.response;

import ang.gimozzi.kickdealbase.domain.apply.Apply;

import java.util.List;

public record ApplyListResponse(
        List<ApplyResponse> applies
) {
    public static ApplyListResponse from(List<Apply> applies){
        return new ApplyListResponse(
                applies.stream()
                        .map(ApplyResponse::from)
                        .toList()
        );
    }
}
