package ang.gimozzi.kickdealbase.infrastructure;

import ang.gimozzi.kickdealbase.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
