package ang.gimozzi.kickdealbase.presentation.pay;

import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.toss.TossResponseService;
import ang.gimozzi.kickdealbase.infrastructure.toss.TossService;
import ang.gimozzi.kickdealbase.presentation.pay.dto.PaymentRequest;
import ang.gimozzi.kickdealbase.presentation.pay.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequiredArgsConstructor
public class PayController {

    private final TossService tossService;
    private final TossResponseService tossResponseService;

    @PostMapping("/confirm")
    public PaymentResponse payment(
            @AuthenticationPrincipal User user,
            @RequestBody PaymentRequest request
    ){
        try{
            HttpResponse toss = tossService.requestConfirm(request);
            return tossResponseService.response(toss);
        }catch(Exception e){
            throw new RuntimeException("tlfvo", e);
        }
    }
}
