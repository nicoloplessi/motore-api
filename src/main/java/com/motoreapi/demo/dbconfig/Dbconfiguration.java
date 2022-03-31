package com.motoreapi.demo.dbconfig;

/*
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration

public class Dbconfiguration {
 
	//@Resource public Environment env;
	
	@Autowired
	   private Environment env;
	
	DriverManagerDataSource d;
	@PostConstruct
	public void init()
    {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
	    dataSource.setUrl(env.getProperty("spring.datasource.url"));
	    dataSource.setUsername(env.getProperty("spring.datasource.username"));
	    dataSource.setPassword(env.getProperty("spring.datasource.password"));
	
	    d=dataSource;
    }
	@Primary
	@Bean(name = "db1")
	@PostConstruct
	public DataSource dataSource1() {
		
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource=d;

	    return dataSource;
	}

 @Bean(name = "jdbcTemplate1")
@Primary
 public JdbcTemplate jdbcTemplate1(@Qualifier("db1") DataSource ds) {
  return new JdbcTemplate(ds);
 }
 
 @Bean(name = "db2")
 @PostConstruct
 @ConfigurationProperties(prefix = "spring.thirddatasource")
 public DataSource dataSource2() {
  return  DataSourceBuilder.create().build();
 }

 @Bean(name = "jdbcTemplate2")

 public JdbcTemplate jdbcTemplate2(@Qualifier("db2") DataSource ds) {
  return new JdbcTemplate(ds);
 }

}*/