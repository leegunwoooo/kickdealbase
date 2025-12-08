package ang.gimozzi.kickdealbase.shared.aop;

import ang.gimozzi.kickdealbase.shared.annotation.Admin;
import ang.gimozzi.kickdealbase.shared.annotation.ValidUser;
import org.aspectj.lang.JoinPoint;
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

    @Before("@annotation(admin)")
    public void checkAdmin(JoinPoint joinPoint, Admin admin) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("관리자만 접근 가능합니다.");
        }
    }

    @Before("@annotation(validUser)")
    public void checkUser(JoinPoint joinPoint, ValidUser validUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BANNED"))) {
            throw new AccessDeniedException("밴당하셨습니다.");
        }
    }

}
