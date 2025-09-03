package ang.gimozzi.kickdealbase.presentation.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
