package ang.gimozzi.kickdealbase.domain.apply;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplyStatus {
    WAITING("대기중"),
    REJECT("반려됨");

    private final String description;
}
