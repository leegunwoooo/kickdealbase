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

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        }

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                try {
                    Claims claims = tokenService.parseToken(token);
                    Long id = claims.get("id", Long.class);

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
                    } else {
                        log.error("sessionAttributes가 null");
                    }

                } catch (Exception e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            } else {
                throw new IllegalArgumentException("Authorization 헤더 없음");
            }
        }

        else if (StompCommand.SEND.equals(accessor.getCommand()) ||
                StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {

            if (accessor.getUser() != null) {
                return message;
            }

            Map<String, Object> sessionAttrs = accessor.getSessionAttributes();

            if (sessionAttrs == null) {
                throw new IllegalArgumentException("세션이 없습니다");
            }

            UserPrincipal principal = (UserPrincipal) sessionAttrs.get("userPrincipal");

            if (principal != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + principal.role().name()))
                        );
                accessor.setUser(authentication);
            } else {
                throw new IllegalArgumentException("인증 정보 없음");
            }
        } else {
            log.info("다른 command: {}", accessor.getCommand());
        }

        return message;
    }
}