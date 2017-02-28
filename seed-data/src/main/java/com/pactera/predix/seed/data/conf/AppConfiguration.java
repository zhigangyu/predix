package com.pactera.predix.seed.data.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class AppConfiguration{
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost/lathe");
		dataSource.setUsername("lathe");
		dataSource.setPassword("lathe");
		
		//dataSource.setUrl("jdbc:postgres://ue0fcf689a0444b429a11bbc7359225b2:d22f5ce2b8bb4951a4deb694f5c5a417@10.72.6.121:5432/de0fcf689a0444b429a11bbc7359225b2?sslmode=disable");
		//dataSource.setUsername("ue0fcf689a0444b429a11bbc7359225b2");
		//dataSource.setPassword("d22f5ce2b8bb4951a4deb694f5c5a417");
		return dataSource;
	}
}


