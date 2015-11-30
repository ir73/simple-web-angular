package com.sappadev.simplewebangular.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;

@Service
/**
 * @author (<a href="mailto:sergei.ledvanov@gmail.com">Sergei Ledvanov</a>) - 30.11.2015.
 */
public class AuthServiceImpl implements AuthService {
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(Principal principal) {
		return ((Authentication) principal).getAuthorities();
	}
}
