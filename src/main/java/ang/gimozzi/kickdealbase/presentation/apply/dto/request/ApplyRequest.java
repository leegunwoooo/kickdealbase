package ang.gimozzi.kickdealbase.presentation.apply.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyRequest {

    @NotNull(message = "필수값입니다.")
    private Integer point;

}
