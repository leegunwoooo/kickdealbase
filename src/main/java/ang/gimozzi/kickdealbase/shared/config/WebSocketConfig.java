package ang.gimozzi.kickdealbase.shared.config;

import ang.gimozzi.kickdealbase.infrastructure.websocket.WebSocketAuthChannelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketAuthChannelInterceptor authInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("🔧 WebSocket 엔드포인트 등록: /ws-chat");
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        System.out.println("🔧 MessageBroker 설정: prefix=/app, broker=/topic");
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        System.out.println("🔧 Interceptor 등록 시작");
        System.out.println("🔧 authInterceptor: " + authInterceptor);
        registration.interceptors(authInterceptor);
        System.out.println("🔧 Interceptor 등록 완료!");
    }

    //Boolean isSsibal = true;
}

