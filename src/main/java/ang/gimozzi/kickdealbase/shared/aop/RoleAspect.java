package ang.gimozzi.kickdealbase.shared.aop;

import ang.gimozzi.kickdealbase.shared.annotation.Admin;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class RoleAspect {

    @Before("@annotation(admin)")
    public void checkAdmin(JoinPoint joinPoint, Admin admin) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            throw new IllegalArgumentException("관리자만 접근 가능합니다.");
        }
    }

}
