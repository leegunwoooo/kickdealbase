package ang.gimozzi.kickdealbase.application.auth;

import ang.gimozzi.kickdealbase.infrastructure.jwt.dto.response.TokenResponse;
import ang.gimozzi.kickdealbase.infrastructure.jwt.service.TokenService;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RefreshUseCase {

    private final TokenService tokenService;

    public TokenResponse execute(String refreshToken) {
        return new TokenResponse(
                tokenService.refreshAccessToken(refreshToken),
                refreshToken
        );
    }

}
