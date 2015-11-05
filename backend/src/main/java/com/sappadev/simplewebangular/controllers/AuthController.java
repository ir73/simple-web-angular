package com.sappadev.simplewebangular.controllers;

import com.sappadev.simplewebangular.controllers.vo.ResponseJson;
import com.sappadev.simplewebangular.controllers.vo.SuccessResponseJson;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/user")
    /**
     * Handles user request and replies with logged in information such as
     * roles and user's user name
     */
    public ResponseJson user(Principal principal) {
        GetUserDetailsResponseJson json = new GetUserDetailsResponseJson();
        json.username = principal.getName();
        json.authorities = ((Authentication) principal).getAuthorities();
        return json;
    }

    static class GetUserDetailsResponseJson extends SuccessResponseJson {
        public String username;
        public Collection<? extends GrantedAuthority> authorities;
    }
}
