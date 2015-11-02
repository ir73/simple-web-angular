package com.sappadev.simplewebangular.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by serge_000 on 1.11.2015.
 */
public class Authority implements GrantedAuthority {
    private final String role;

    public Authority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
