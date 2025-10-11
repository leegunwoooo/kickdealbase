package ang.gimozzi.kickdealbase.presentation.pay.dto;

import lombok.Getter;

@Getter
public class PaymentResponse {

    private String orderId;

    private String paymentKey;

    private String status;

    private int totalAmount;

    private String approvedAt;

    public PaymentResponse(String orderId, String paymentKey, String status, int totalAmount, String approvedAt) {
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.status = status;
        this.totalAmount = totalAmount;
        this.approvedAt = approvedAt;
    }
}
