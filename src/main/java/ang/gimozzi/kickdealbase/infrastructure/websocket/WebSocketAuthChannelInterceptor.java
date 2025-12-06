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
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                try {
                    Claims claims = tokenService.parseToken(token);
                    System.out.println("claims=" + claims);
                    Long id = claims.get("id", Long.class);
                    System.out.println("id=" + id);
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
                                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                            );

                    accessor.setUser(authentication);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            } else {
                throw new IllegalArgumentException("몰루");
            }
        }

        return message;
    }
}
