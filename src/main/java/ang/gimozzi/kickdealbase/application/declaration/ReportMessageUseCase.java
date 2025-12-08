package ang.gimozzi.kickdealbase.application.declaration;

import ang.gimozzi.kickdealbase.domain.message.Message;
import ang.gimozzi.kickdealbase.domain.message.service.MessageFacade;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.declaration.dto.request.DeclarationRequest;
import ang.gimozzi.kickdealbase.presentation.declaration.dto.response.DeclarationResponse;
import ang.gimozzi.kickdealbase.shared.annotation.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class ReportMessageUseCase {

    private final DeclarationFixture declarationFixture;
    private final MessageFacade messageFacade;

    @Transactional
    public DeclarationResponse reportMessage(Long id, DeclarationRequest declarationRequest) {
        Message message = messageFacade.getMessage(id);

        User badMan = message.getSender();

        return DeclarationResponse.from(
                declarationFixture.reportMessage(badMan,  message, declarationRequest.getReason())
        );
    }

}
