package ang.gimozzi.kickdealbase.infrastructure.websocket;

import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.jwt.service.TokenService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    private final TokenService tokenService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("=====================================");
        log.info("🚨🚨🚨 INTERCEPTOR 실행됨!!!");
        log.info("=====================================");

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info("accessor: {}", accessor);

        if (accessor == null) {
            log.error("❌ accessor가 null");
            return message;
        }

        log.info("📍 StompCommand: {}", accessor.getCommand());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("🔌 CONNECT 처리 시작");
            String token = accessor.getFirstNativeHeader("Authorization");
            log.info("🎫 Authorization 헤더: {}", token != null ? "있음" : "없음");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                try {
                    Claims claims = tokenService.parseToken(token);
                    log.info("claims={}", claims);
                    Long id = claims.get("id", Long.class);
                    log.info("id={}", id);

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

                    Map<String, Object> sessionAttrs = accessor.getSessionAttributes();
                    if (sessionAttrs != null) {
                        sessionAttrs.put("userPrincipal", principal);
                        log.info("✅ 세션에 userPrincipal 저장: {}", principal);
                    } else {
                        log.error("❌ sessionAttributes가 null!");
                    }

                    log.info("✅ CONNECT 성공 - userId: {}", user.getId());

                } catch (Exception e) {
                    log.error("❌ CONNECT 인증 실패: {}", e.getMessage(), e);
                    throw new IllegalArgumentException(e.getMessage());
                }
            } else {
                log.error("❌ Authorization 헤더 없음");
                throw new IllegalArgumentException("Authorization 헤더 없음");
            }
        }

        else if (StompCommand.SEND.equals(accessor.getCommand()) ||
                StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {

            log.info("📤 SEND/SUBSCRIBE 처리 - command: {}", accessor.getCommand());

            if (accessor.getUser() != null) {
                log.info("✅ 이미 인증됨: {}", accessor.getUser().getName());
                return message;
            }

            log.info("🔄 세션에서 복원 시도");
            Map<String, Object> sessionAttrs = accessor.getSessionAttributes();
            log.info("📦 sessionAttributes: {}", sessionAttrs);
            log.info("📦 sessionId: {}", accessor.getSessionId());

            if (sessionAttrs == null) {
                log.error("❌ sessionAttributes가 null!");
                throw new IllegalArgumentException("세션이 없습니다");
            }

            UserPrincipal principal = (UserPrincipal) sessionAttrs.get("userPrincipal");
            log.info("👤 복원된 principal: {}", principal);

            if (principal != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + principal.role().name()))
                        );
                accessor.setUser(authentication);
                log.info("✅ 인증 복원 성공 - userId: {}", principal.id());
            } else {
                log.error("❌ 세션에 userPrincipal 없음!");
                log.error("❌ sessionAttributes keys: {}", sessionAttrs.keySet());
                throw new IllegalArgumentException("인증 정보 없음");
            }
        } else {
            log.info("ℹ️ 다른 command: {}", accessor.getCommand());
        }

        log.info("🏁 Interceptor 종료");
        log.info("=====================================");
        return message;
    }
}