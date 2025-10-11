package ang.gimozzi.kickdealbase.presentation.pay.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    private String orderId;

    private Integer amount;

    private String paymentKey;

}
