package ang.gimozzi.kickdealbase.application;

import ang.gimozzi.kickdealbase.domain.Role;
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

        valid(request);

        return new UserResponse(
                userRepository.save(
                        User.builder()
                                .email(request.getEmail())
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .build()
        ));
    }

    public void valid(SignUpRequest request){
        if(userRepository.existsByUsername(request.getUsername())||userRepository.existsByEmail(request.getEmail())){
            throw new IllegalStateException("이미 존재하는 닉네임 또는 이메일입니다.");
        }
    }

}
