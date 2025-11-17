package ang.gimozzi.kickdealbase.application.user;

import ang.gimozzi.kickdealbase.domain.user.SignUpVerification;
import ang.gimozzi.kickdealbase.infrastructure.persistence.SignUpVerificationRepository;
import ang.gimozzi.kickdealbase.presentation.user.dto.request.VerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationValidUseCase {

    private final SignUpVerificationRepository signUpVerificationRepository;

    public void verify(VerifyRequest request){
        SignUpVerification signUpVerification = signUpVerificationRepository.findById(request.getEmail())
                        .orElseThrow(() -> new IllegalArgumentException("인증 코드를 찾지 못했습니다."));

        signUpVerification.validateCode(request.getCode());

        signUpVerification.verify();

        signUpVerificationRepository.save(signUpVerification);
    }

}
