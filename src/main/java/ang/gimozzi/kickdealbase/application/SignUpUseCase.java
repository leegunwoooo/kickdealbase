package ang.gimozzi.kickdealbase.application;

import ang.gimozzi.kickdealbase.domain.User;
import ang.gimozzi.kickdealbase.infrastructure.UserRepository;
import ang.gimozzi.kickdealbase.presentation.SignUpRequest;
import ang.gimozzi.kickdealbase.presentation.UserResponse;
import ang.gimozzi.kickdealbase.shared.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class SignUpUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse execute(SignUpRequest request){
        return new UserResponse(
                userRepository.save(
                        User.builder()
                                .email(request.getEmail())
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .build()
        ));
    }

}
