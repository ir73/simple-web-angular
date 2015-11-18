package com.sappadev.simplewebangular.conf;

import com.sappadev.simplewebangular.security.BasicJsonEntryPoint;
import com.sappadev.simplewebangular.services.CustomerService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
//		http.addFilterBefore(basicAuthenticationFilter(), BasicAuthenticationFilter.class);
	}

//	@Override
//	protected AuthenticationManager authenticationManager() throws Exception {
//		return super.authenticationManager();
//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customerService);
	}

//	@Override
//	@Bean
//	protected AuthenticationManager authenticationManager() throws Exception {
//		return super.authenticationManager();
//	}

//	@Bean
//	public BasicAuthenticationFilter basicAuthenticationFilter() throws Exception {
//		return new BasicAuthenticationFilter(authenticationManager(), basicJsonEntryPoint);
//	}
}


