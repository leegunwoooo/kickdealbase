package ang.gimozzi.kickdealbase.infrastructure.jwt.service;

import ang.gimozzi.kickdealbase.domain.user.Role;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.UserRepository;
import ang.gimozzi.kickdealbase.infrastructure.jwt.domain.RefreshToken;
import ang.gimozzi.kickdealbase.infrastructure.jwt.domain.RefreshTokenRepository;
import ang.gimozzi.kickdealbase.infrastructure.jwt.domain.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(User user) {
        Long accessTokenExpiration = 60 * 60 * 1000L;

        return generateToken(user.getId(), user.getRole(), TokenType.ACCESS_TOKEN, accessTokenExpiration);
    }

    public String generateRefreshToken(User user) {
        Long refreshTokenExpiration = 7 * 24 * 60 * 60 * 1000L;

        String token = generateToken(user.getId(), user.getRole(), TokenType.REFRESH_TOKEN, refreshTokenExpiration);

        refreshTokenRepository.save(RefreshToken.create(user.getId(), token));

        return token;
    }

    public String generateToken(Long id, Role role, TokenType type, Long time) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        claims.put("role", role.toString());
        claims.put("type", type.name());
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + time))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String refreshAccessToken(String refreshToken){
        Claims claims = parseToken(refreshToken);

        String type = claims.get("type", String.class);
        if (!TokenType.REFRESH_TOKEN.name().equals(type)) {
            throw new IllegalArgumentException("Invalid token type");
        }

        Long id = claims.get("id", Long.class);
        RefreshToken token = refreshTokenRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ㅗ"));

        if (!token.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("응");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ㅇㅇ"));

        return generateAccessToken(user);
    }


    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getType(String token) {
        return parseToken(token).get("type", String.class);
    }

    public User getUserId(Long id) throws IOException {
        return userRepository.findById(id)
                .orElseThrow(() -> new IOException("ff"));
    }
}
