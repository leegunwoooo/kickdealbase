package ang.gimozzi.kickdealbase.presentation.user;

import ang.gimozzi.kickdealbase.application.user.LoginUseCase;
import ang.gimozzi.kickdealbase.application.user.LogoutUseCase;
import ang.gimozzi.kickdealbase.application.user.RefreshUseCase;
import ang.gimozzi.kickdealbase.application.user.SignUpUseCase;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.user.dto.request.LoginRequest;
import ang.gimozzi.kickdealbase.presentation.user.dto.request.SignUpRequest;
import ang.gimozzi.kickdealbase.presentation.user.dto.response.UserResponse;
import ang.gimozzi.kickdealbase.infrastructure.jwt.dto.response.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final RefreshUseCase refreshUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> signUp(
            @RequestBody @Valid SignUpRequest request){
        return ResponseEntity.ok(signUpUseCase.execute(request));
    }

    @PatchMapping
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid LoginRequest request
    ){
        return ResponseEntity.ok(loginUseCase.execute(request));
    }

    @GetMapping
    public ResponseEntity<TokenResponse> refreshToken(
            @RequestHeader("Refresh-token") String refreshToken
    ){
        return ResponseEntity.ok(refreshUseCase.execute(refreshToken));
    }

    @PutMapping
    public void logout(
            @AuthenticationPrincipal User user
    ){
        logoutUseCase.execute(user);
    }

}
