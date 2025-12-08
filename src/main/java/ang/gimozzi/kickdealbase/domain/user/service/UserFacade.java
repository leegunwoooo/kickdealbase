package ang.gimozzi.kickdealbase.domain.user.service;

import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("찾지못함 유저"));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾지 못함"));
    }
}
