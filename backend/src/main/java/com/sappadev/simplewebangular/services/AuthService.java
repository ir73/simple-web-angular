package com.sappadev.simplewebangular.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

/**
 * @author (<a href="mailto:sergei.ledvanov@gmail.com">Sergei Ledvanov</a>) - 30.11.2015.
 */
public interface AuthService {

	@PreAuthorize("isAuthenticated()")
	Collection<? extends GrantedAuthority> getAuthorities(Principal principal);
}
