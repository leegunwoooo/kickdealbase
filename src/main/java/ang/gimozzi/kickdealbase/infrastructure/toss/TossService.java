package ang.gimozzi.kickdealbase.infrastructure.toss;

import ang.gimozzi.kickdealbase.presentation.pay.dto.PaymentRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class TossService {

    @Value("${payment.secret-key}")
    private String secretKey;

    @Value("${payment.base-url}")
    private String baseUrl;

    @Value("${payment.confirm-endpoint}")
    private String endpoint;

    private final ObjectMapper objectMapper;

    public HttpResponse requestConfirm(PaymentRequest request) throws IOException, InterruptedException {

        String header = "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes());

        String orderId = request.getOrderId();
        String price = request.getAmount().toString();
        String paymentKey = request.getPaymentKey();

        JsonNode tossRequest = objectMapper.createObjectNode()
                .put("orderId", orderId)
                .put("amount", price)
                .put("paymentKey", paymentKey);

        String requestBody = objectMapper.writeValueAsString(tossRequest);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .header("Authorization", header)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

}
