package ang.gimozzi.kickdealbase.infrastructure.websocket;

import ang.gimozzi.kickdealbase.domain.user.Role;

public record UserPrincipal(Long id, String username, Role role) {}

