package ang.gimozzi.kickdealbase.application.user;

import ang.gimozzi.kickdealbase.domain.user.Role;
import ang.gimozzi.kickdealbase.domain.user.SignUpVerification;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.SignUpVerificationRepository;
import ang.gimozzi.kickdealbase.infrastructure.persistence.UserRepository;
import ang.gimozzi.kickdealbase.presentation.user.dto.request.SignUpRequest;
import ang.gimozzi.kickdealbase.presentation.user.dto.response.UserResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class SignUpUseCase {

    private final UserRepository userRepository;
    private final SignUpVerificationRepository signUpVerificationRepository;
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

        SignUpVerification signUpVerification = signUpVerificationRepository.findById(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("인증코드를 찾지 못함"));

        signUpVerification.validateVerified();

        signUpVerificationRepository.delete(signUpVerification);
    }

}
