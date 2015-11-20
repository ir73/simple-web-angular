package com.sappadev.simplewebangular.conf;

import com.sappadev.simplewebangular.security.BasicJsonEntryPoint;
import com.sappadev.simplewebangular.services.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true,
		prePostEnabled = true,
		securedEnabled = true)
/**
 * @author (<a href="mailto:sergei.ledvanov@gmail.com">Sergei Ledvanov</a>) - 11/18/2015.
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	CustomerService customerService;

	@Resource
	BasicJsonEntryPoint basicJsonEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		http.logout().logoutSuccessUrl("/");
		http.addFilterBefore(basicAuthenticationFilter(), BasicAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customerService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	/**
	 * Entry point is required so that we would always get a JSON error instead of
	 * just an error code
	 */
	public BasicAuthenticationFilter basicAuthenticationFilter() throws Exception {
		return new BasicAuthenticationFilter(authenticationManager(), basicJsonEntryPoint);
	}
}


