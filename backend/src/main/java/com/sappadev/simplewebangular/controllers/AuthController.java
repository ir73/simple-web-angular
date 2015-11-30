package com.sappadev.simplewebangular.controllers;

import com.sappadev.simplewebangular.controllers.vo.ResponseJson;
import com.sappadev.simplewebangular.controllers.vo.SuccessResponseJson;
import com.sappadev.simplewebangular.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

@RestController
/**
 * Authentication controller handles requests related to authentication
 */
public class AuthController {

	@Autowired
	AuthService authService;

    @RequestMapping("/api/user")
    /**
     * Handles user request and replies with logged in information such as
     * roles and user's user name
     */
    public ResponseJson user(Principal principal) {
	    Collection<? extends GrantedAuthority> grantedAuthorities
			    = authService.getAuthorities(principal);

	    return new SuccessResponseJson() {
		    public String username = principal.getName();
		    public Collection<? extends GrantedAuthority> authorities = grantedAuthorities;
	    };
    }
}
