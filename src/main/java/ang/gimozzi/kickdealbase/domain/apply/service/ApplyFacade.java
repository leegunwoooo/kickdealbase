package ang.gimozzi.kickdealbase.domain.apply.service;

import ang.gimozzi.kickdealbase.domain.apply.Apply;
import ang.gimozzi.kickdealbase.infrastructure.persistence.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApplyFacade {

    private final ApplyRepository applyRepository;

    public Apply findById(Long id){
        return applyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청입니다"));
    }

}
