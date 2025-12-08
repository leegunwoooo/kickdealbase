package ang.gimozzi.kickdealbase.presentation.declaration.dto.response;

import ang.gimozzi.kickdealbase.domain.declaration.Declaration;
import ang.gimozzi.kickdealbase.domain.declaration.DeclarationStatus;
import ang.gimozzi.kickdealbase.domain.declaration.Type;

public record DeclarationResponse(
        Long id,
        String title,
        String reason,
        DeclarationStatus status,
        Type type
) {
    public static DeclarationResponse from(Declaration declaration) {
        String title;
        if (declaration.getType() == Type.MESSAGE) {
            title = (declaration.getMessage() != null)
                    ? declaration.getMessageContent()
                    : "(삭제된 메시지)";
        } else {
            title = (declaration.getProduct() != null)
                    ? declaration.getProductName()
                    : "(삭제된 상품)";
        }
        return new DeclarationResponse(
                declaration.getId(),
                title,
                declaration.getReason(),
                declaration.getStatus(),
                declaration.getType()
        );
    }
}
