package ang.gimozzi.kickdealbase.infrastructure.websocket;

import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.jwt.service.TokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    private final TokenService tokenService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("=====================================");
        System.out.println("🚨🚨🚨 INTERCEPTOR 실행됨!!! 🚨🚨🚨");
        System.out.println("=====================================");

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        System.out.println("accessor: " + accessor);

        if (accessor == null) {
            System.out.println("❌ accessor가 null");
            return message;
        }

        System.out.println("📍 StompCommand: " + accessor.getCommand());

        // CONNECT: 최초 인증
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            System.out.println("🔌 CONNECT 처리 시작");
            String token = accessor.getFirstNativeHeader("Authorization");
            System.out.println("🎫 Authorization 헤더: " + token);

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                try {
                    Claims claims = tokenService.parseToken(token);
                    System.out.println("claims=" + claims);
                    Long id = claims.get("id", Long.class);
                    System.out.println("id=" + id);

                    if (id == null) {
                        throw new IllegalArgumentException("토큰에 id가 없습니다");
                    }

                    User user = tokenService.getUserId(id);

                    if (user == null) {
                        throw new IllegalArgumentException("유효하지 않은 토큰");
                    }

                    UserPrincipal principal = new UserPrincipal(
                            user.getId(),
                            user.getUsername(),
                            user.getRole()
                    );

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    principal,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                            );

                    accessor.setUser(authentication);
                    accessor.getSessionAttributes().put("userPrincipal", principal);

                    System.out.println("✅ CONNECT 성공 - userId: " + user.getId());

                } catch (Exception e) {
                    System.err.println("❌ CONNECT 인증 실패: " + e.getMessage());
                    e.printStackTrace();
                    throw new IllegalArgumentException(e.getMessage());
                }
            } else {
                System.err.println("❌ Authorization 헤더 없음");
                throw new IllegalArgumentException("Authorization 헤더 없음");
            }
        }

        // SEND, SUBSCRIBE: 세션에서 복원
        else if (StompCommand.SEND.equals(accessor.getCommand()) ||
                StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {

            System.out.println("📤 SEND/SUBSCRIBE 처리 - command: " + accessor.getCommand());

            if (accessor.getUser() != null) {
                System.out.println("✅ 이미 인증됨");
                return message;
            }

            System.out.println("🔄 세션에서 복원 시도");
            System.out.println("📦 sessionAttributes: " + accessor.getSessionAttributes());

            UserPrincipal principal = (UserPrincipal) accessor.getSessionAttributes().get("userPrincipal");
            System.out.println("👤 복원된 principal: " + principal);

            if (principal != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + principal.role().name()))
                        );
                accessor.setUser(authentication);
                System.out.println("✅ 인증 복원 성공 - userId: " + principal.id());
            } else {
                System.err.println("❌ 세션에 userPrincipal 없음!");
                throw new IllegalArgumentException("인증 정보 없음");
            }
        } else {
            System.out.println("ℹ️ 다른 command: " + accessor.getCommand());
        }

        System.out.println("🏁 Interceptor 종료");
        System.out.println("=====================================");
        return message;
    }
}