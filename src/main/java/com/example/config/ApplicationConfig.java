package com.example.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })

@EntityScan(basePackages="com.example.model")
@EnableJpaRepositories(basePackages= "com.example.dao")
public class ApplicationConfig {
	@Autowired
	private Environment env;

//	@Autowired
//	private EntityManagerFactory entityManagerFactory;

	@Bean
	public DataSourceTransactionManager transactionManager() {
	    final DataSourceTransactionManager txManager = new DataSourceTransactionManager();
	    txManager.setDataSource(dataSource());

	    return txManager;
	}
	
	//handle error missing entityManagerFactory
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ClassNotFoundException {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan(env.getRequiredProperty("entitymanager.packagesToScan"));
        entityManagerFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        Properties jpaProterties = new Properties();
        jpaProterties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        jpaProterties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        jpaProterties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));

        entityManagerFactory.setJpaProperties(jpaProterties);

        return entityManagerFactory;
    }

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
		return dataSource;
	}
}
