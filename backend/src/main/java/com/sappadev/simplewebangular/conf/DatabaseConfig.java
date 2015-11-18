package com.sappadev.simplewebangular.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
/**
 * @author (<a href="mailto:sergei.ledvanov@gmail.com">Sergei Ledvanov</a>) - 11/18/2015.
 */
public class DatabaseConfig {

	@Resource
	Environment environment;

	@Bean
	public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

//	@Bean
//	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//		final DataSourceInitializer initializer = new DataSourceInitializer();
//		initializer.setDataSource(dataSource);
//		initializer.setDatabasePopulator(databasePopulator());
//		return initializer;
//	}
//
//	private DatabasePopulator databasePopulator() {
//		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//		populator.addScript(new FileSystemResource("classpath:/db/sql/create-db.sql"));
//		populator.addScript(new FileSystemResource("classpath:/db/sql/insert-data.sql"));
//		return populator;
//	}


	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:/db/sql/create-db.sql")
				.addScript("classpath:/db/sql/insert-data.sql")
                .build();
//
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
//		dataSource.setUsername(environment.getRequiredProperty("jdbc.user"));
//		dataSource.setPassword(environment.getRequiredProperty("jdbc.pwd"));
//		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) throws Exception {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPersistenceUnitName("CustomersPU");
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.sappadev.simplewebangular.data.domain");
		factory.setDataSource(dataSource);

		factory.setJpaProperties(jpaProperties());

		return factory;
	}

	Properties jpaProperties() {
		Properties props = new Properties();
		props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		props.put("hibernate.hbm2ddl.auto", "validate");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.format_sql", "true");

		return props;
	}
}
