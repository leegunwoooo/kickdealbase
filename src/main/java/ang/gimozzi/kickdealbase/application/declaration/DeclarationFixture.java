package ang.gimozzi.kickdealbase.application.declaration;

import ang.gimozzi.kickdealbase.domain.declaration.Declaration;
import ang.gimozzi.kickdealbase.domain.message.Message;
import ang.gimozzi.kickdealbase.domain.product.Product;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.infrastructure.persistence.DeclarationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class DeclarationFixture {

    private final DeclarationRepository declarationRepository;

    public Declaration reportMessage(User user, Message message, String reason) {
        return declarationRepository.save(
                new Declaration(user, message, reason)
        );
    }

    public Declaration reportProduct(User user, Product product, String reason) {
        return declarationRepository.save(
                new Declaration(user, product, reason)
        );
    }

}
