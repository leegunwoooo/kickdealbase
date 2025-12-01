package ang.gimozzi.kickdealbase.infrastructure.persistence;

import ang.gimozzi.kickdealbase.domain.user.SignUpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpVerificationRepository extends JpaRepository<SignUpVerification, String> {
}
