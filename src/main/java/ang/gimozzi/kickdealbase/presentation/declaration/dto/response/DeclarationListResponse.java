package ang.gimozzi.kickdealbase.presentation.declaration.dto.response;

import ang.gimozzi.kickdealbase.domain.declaration.Declaration;

import java.util.List;

public record DeclarationListResponse(
        List<DeclarationResponse> declarations
) {
    public static DeclarationListResponse from(List<Declaration> declarations) {
        return new DeclarationListResponse(
                declarations.stream()
                        .map(DeclarationResponse::from)
                        .toList()
        );
    }
}
