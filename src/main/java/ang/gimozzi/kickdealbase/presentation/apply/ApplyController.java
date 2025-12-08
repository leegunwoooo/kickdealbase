package ang.gimozzi.kickdealbase.presentation.apply;

import ang.gimozzi.kickdealbase.application.point.ApplyChargePointUseCase;
import ang.gimozzi.kickdealbase.application.point.ApproveApplyUseCase;
import ang.gimozzi.kickdealbase.application.point.RejectApplyUseCase;
import ang.gimozzi.kickdealbase.domain.user.User;
import ang.gimozzi.kickdealbase.presentation.apply.dto.request.ApplyRequest;
import ang.gimozzi.kickdealbase.presentation.apply.dto.response.ApplyResponse;
import ang.gimozzi.kickdealbase.shared.annotation.Admin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applies")
public class ApplyController {

    private final ApproveApplyUseCase approveApplyUseCase;
    private final ApplyChargePointUseCase applyChargePointUseCase;
    private final RejectApplyUseCase rejectApplyUseCase;

    @PostMapping
    public ApplyResponse apply(
            @Valid @RequestBody ApplyRequest request,
            @AuthenticationPrincipal User user
    ) {
        return applyChargePointUseCase.apply(request, user);
    }

    @Admin
    @PatchMapping("/approve/{id}")
    public void Approve(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        approveApplyUseCase.apply(id);
    }

    @Admin
    @PatchMapping("/reject/{id}")
    public ApplyResponse reject(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ){
        return rejectApplyUseCase.reject(id);
    }

}
