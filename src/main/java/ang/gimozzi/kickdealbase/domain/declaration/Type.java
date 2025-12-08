package ang.gimozzi.kickdealbase.domain.declaration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {
    PRODUCT("상품"),
    MESSAGE("메시지");

    private final String description;
}
