package ang.gimozzi.kickdealbase.infrastructure.oauth2;

import ang.gimozzi.kickdealbase.infrastructure.jwt.dto.response.TokenResponse;
import ang.gimozzi.kickdealbase.infrastructure.jwt.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        String accessToken = tokenService.generateAccessToken(principalDetails.getUser());
        String refreshToken = tokenService.generateRefreshToken(principalDetails.getUser());

        TokenResponse tokenResponse = new TokenResponse(accessToken, refreshToken);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(tokenResponse));

        clearAuthenticationAttributes(request);
    }

}