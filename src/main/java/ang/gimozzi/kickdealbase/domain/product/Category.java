package ang.gimozzi.kickdealbase.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {
    SOCCER_SHOE("축구화"),
    FOOTBALL_SHOE("풋살화"),
    UNIFORM("유니폼"),
    SOCCER_BALL("축구공"),
    YOUTH("유소년"),
    GITA("기타용품"),
    GOALKEEPER("GK용품");

    private final String description;
}
