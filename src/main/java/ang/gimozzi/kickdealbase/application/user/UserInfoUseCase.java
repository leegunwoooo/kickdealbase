package ang.gimozzi.kickdealbase.application.user;

import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.UserRepository;
import ang.gimozzi.kickdealbase.presentation.user.dto.response.UserResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class UserInfoUseCase {

    private final UserRepository userRepository;

    public UserResponse getUserInfo(User user) {
        return new UserResponse(userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"))
        );
    }
}
