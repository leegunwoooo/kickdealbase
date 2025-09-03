package ang.gimozzi.kickdealbase.infrastructure;

import ang.gimozzi.kickdealbase.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
