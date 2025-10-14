package ang.gimozzi.kickdealbase.application.user;

import ang.gimozzi.kickdealbase.domain.user.SignUpVerification;
import ang.gimozzi.kickdealbase.infrastructure.mail.MailUtil;
import ang.gimozzi.kickdealbase.infrastructure.persistence.SignUpVerificationRepository;
import ang.gimozzi.kickdealbase.presentation.user.dto.request.SendVerificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendVerificationUseCase {

    private final SignUpVerificationRepository signUpVerificationRepository;
    private final MailUtil mailUtil;

    public void sendVerificationCode(SendVerificationRequest request) {
        SignUpVerification signUpVerification = new SignUpVerification(request.getEmail());

        mailUtil.sendMimeMessage(signUpVerification.getEmail(), signUpVerification.getCode());

        signUpVerificationRepository.save(signUpVerification);
    }

}
