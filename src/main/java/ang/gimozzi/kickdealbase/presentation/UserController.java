package ang.gimozzi.kickdealbase.presentation;

import ang.gimozzi.kickdealbase.application.LoginUseCase;
import ang.gimozzi.kickdealbase.application.SignUpUseCase;
import ang.gimozzi.kickdealbase.shared.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> signUp(
            @RequestBody @Valid SignUpRequest request){
        return ResponseEntity.ok(signUpUseCase.execute(request));
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid LoginRequest request
    ){
        return ResponseEntity.ok(loginUseCase.execute(request));
    }

}
