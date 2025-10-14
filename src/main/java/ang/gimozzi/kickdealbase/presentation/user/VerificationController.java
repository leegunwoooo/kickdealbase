package ang.gimozzi.kickdealbase.presentation.user;

import ang.gimozzi.kickdealbase.application.user.SendVerificationUseCase;
import ang.gimozzi.kickdealbase.application.user.VerificationValidUseCase;
import ang.gimozzi.kickdealbase.presentation.user.dto.request.SendVerificationRequest;
import ang.gimozzi.kickdealbase.presentation.user.dto.request.VerifyRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/verification")
public class VerificationController {

    private final SendVerificationUseCase sendVerificationCodeService;
    private final VerificationValidUseCase verificationValidUseCase;

    @PostMapping
    public void sendVerificationCode(
            @Valid @RequestBody SendVerificationRequest request
    ) {
        sendVerificationCodeService.sendVerificationCode(request);
    }

    @PatchMapping
    public void verification(
            @Valid @RequestBody VerifyRequest request
    ){
        verificationValidUseCase.verify(request);
    }

}
