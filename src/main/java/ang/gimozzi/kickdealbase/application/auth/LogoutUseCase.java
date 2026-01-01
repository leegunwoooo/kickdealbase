package ang.gimozzi.kickdealbase.application.auth;

import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.jwt.domain.RefreshTokenRepository;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RefreshTokenRepository refreshTokenRepository;

    public void execute(User user) {
        refreshTokenRepository.deleteById(user.getId());
    }

}
