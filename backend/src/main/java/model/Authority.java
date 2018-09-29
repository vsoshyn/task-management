package model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    USER_MANAGEMENT, TASK_MANAGEMENT, TASK_VIEW, SYSTEM;

    @Override
    public String getAuthority() {
        return name();
    }
}
