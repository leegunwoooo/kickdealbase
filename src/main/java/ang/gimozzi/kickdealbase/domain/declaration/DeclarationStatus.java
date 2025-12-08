package ang.gimozzi.kickdealbase.domain.declaration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeclarationStatus {
    PROCESSING("처리 중"),
    COMPLETED("범죄자"),
    REJECT("반료됨");

    private final String description;
}
