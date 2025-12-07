package ang.gimozzi.kickdealbase.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {
    ON_SALE("판매중"),
    SOLD_OUT("판매됨");

    private final String description;
}
