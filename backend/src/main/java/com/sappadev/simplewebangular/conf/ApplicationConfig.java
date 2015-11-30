package com.sappadev.simplewebangular.conf;


import com.sappadev.simplewebangular.data.repositories.CustomerRepository;
import com.sappadev.simplewebangular.res.CustomMessageSource;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@Configuration
@ComponentScan({"com.sappadev.simplewebangular.conf",
		"com.sappadev.simplewebangular.controllers",
		"com.sappadev.simplewebangular.data",
		"com.sappadev.simplewebangular.security",
		"com.sappadev.simplewebangular.services"})
@EnableAsync
@EnableScheduling
@EnableJpaRepositories(basePackageClasses = {CustomerRepository.class})
@EnableTransactionManagement
@PropertySources({
		@PropertySource("classpath:app.properties")
})
/**
 * @author (<a href="mailto:sergei.ledvanov@gmail.com">Sergei Ledvanov</a>) - 11/18/2015.
 */
public class ApplicationConfig {

	@Bean
	public MessageSource messageSource() {
		CustomMessageSource messageSource = new CustomMessageSource();
		messageSource.setBasename("classpath:messages/msg");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public SessionLocaleResolver sessionLocaleResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;
	}

	@Bean
	public Mapper mapper() {
		return new DozerBeanMapper(Arrays.asList("dozer.xml"));
	}

}
