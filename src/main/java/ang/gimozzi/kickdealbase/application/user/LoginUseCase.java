package ang.gimozzi.kickdealbase.application.user;

import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.UserRepository;
import ang.gimozzi.kickdealbase.presentation.user.dto.request.LoginRequest;
import ang.gimozzi.kickdealbase.infrastructure.jwt.dto.response.TokenResponse;
import ang.gimozzi.kickdealbase.infrastructure.jwt.service.TokenService;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class LoginUseCase {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public TokenResponse execute(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));

        valid(request.getPassword(), user);

        return new TokenResponse(
                tokenService.generateAccessToken(user),
                tokenService.generateRefreshToken(user)
        );
    }

    public void valid(String rawPassword, User user){
        if(!bCryptPasswordEncoder.matches(rawPassword, user.getPassword())){
            throw new IllegalArgumentException("비밀번호 불일치");
        }
    }
}
