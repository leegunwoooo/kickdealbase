package ang.gimozzi.kickdealbase.presentation.declaration;

import ang.gimozzi.kickdealbase.application.declaration.ApproveDeclarationUseCase;
import ang.gimozzi.kickdealbase.application.declaration.RejectDeclarationUseCase;
import ang.gimozzi.kickdealbase.application.declaration.ReportMessageUseCase;
import ang.gimozzi.kickdealbase.application.declaration.ReportProductUseCase;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.declaration.dto.request.DeclarationRequest;
import ang.gimozzi.kickdealbase.presentation.declaration.dto.response.DeclarationResponse;
import ang.gimozzi.kickdealbase.shared.annotation.Admin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/declarations")
public class DeclarationController {

    private final ReportProductUseCase reportProductUseCase;
    private final ReportMessageUseCase reportMessageUseCase;
    private final ApproveDeclarationUseCase approveDeclarationUseCase;
    private final RejectDeclarationUseCase rejectDeclarationUseCase;

    @PostMapping("/products/{product-id}")
    public DeclarationResponse createProductDeclaration(
            @PathVariable(value = "product-id") Long id,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid DeclarationRequest request
    ){
        return reportProductUseCase.reportProduct(id, request);
    }

    @PostMapping("/messages/{message-id}")
    public DeclarationResponse createMessageDeclaration(
            @PathVariable(value = "message-id") Long id,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid DeclarationRequest request
    ){
        return reportMessageUseCase.reportMessage(id, request);
    }

    @Admin
    @PatchMapping("/{id}")
    public DeclarationResponse approveDeclaration(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return approveDeclarationUseCase.approveDeclaration(id);
    }

    @Admin
    @PutMapping("/{id}")
    public DeclarationResponse rejectDeclaration(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return rejectDeclarationUseCase.rejectDeclaration(id);
    }

}
