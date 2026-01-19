package ang.gimozzi.kickdealbase.shared.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleAspect {

    @Before("@annotation(ang.gimozzi.kickdealbase.shared.annotation.Admin)")
    public void checkAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("관리자만 접근 가능합니다.");
        }
    }

    @Before("@annotation(ang.gimozzi.kickdealbase.shared.annotation.ValidUser)")
    public void checkUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BANNED"))) {
            throw new AccessDeniedException("밴당하셨습니다.");
        }
    }

}
