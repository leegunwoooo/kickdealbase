package ang.gimozzi.kickdealbase.infrastructure.oauth2;

import ang.gimozzi.kickdealbase.domain.user.Role;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);

        String provideId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String username = oauth2User.getAttribute("name");
        String password = "OAuth2";
        Role role = Role.USER;

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            User newUser = User.builder()
                    .email(email)
                    .username(username)
                    .password(password)
                    .role(role)
                    .build();

            userRepository.save(newUser);
            return new PrincipalDetails(newUser, oauth2User.getAttributes());
        } else {
            return new PrincipalDetails(user.get(), oauth2User.getAttributes());
        }
    }
}