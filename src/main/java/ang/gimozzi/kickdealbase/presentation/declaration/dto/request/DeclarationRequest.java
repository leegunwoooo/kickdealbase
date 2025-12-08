package ang.gimozzi.kickdealbase.presentation.declaration.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeclarationRequest {

    @NotEmpty(message = "필수값입니다.")
    private final String reason;

}
