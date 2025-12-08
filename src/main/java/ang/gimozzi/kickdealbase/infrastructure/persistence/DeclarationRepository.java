package ang.gimozzi.kickdealbase.infrastructure.persistence;

import ang.gimozzi.kickdealbase.domain.declaration.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Long> {
}
