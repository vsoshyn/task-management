package model;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

public class CreatedByAware implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(principal -> Objects.equals(principal.getClass(), User.class))
                .map(User.class::cast);
    }
}
