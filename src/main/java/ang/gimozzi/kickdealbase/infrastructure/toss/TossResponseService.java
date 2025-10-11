package ang.gimozzi.kickdealbase.infrastructure.toss;

import ang.gimozzi.kickdealbase.presentation.pay.dto.PaymentResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class TossResponseService {

    private final ObjectMapper objectMapper;

    public PaymentResponse response(HttpResponse<String> httpResponse) {
        try {
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());

            return new PaymentResponse(
                    jsonNode.path("orderId").asText(null),
                    jsonNode.path("paymentKey").asText(null),
                    jsonNode.path("status").asText(null),
                    jsonNode.path("totalAmount").asInt(0),
                    jsonNode.path("approvedAt").asText(null)
            );
        } catch (Exception e) {
            throw new RuntimeException("tlfvo", e);
        }
    }

}
