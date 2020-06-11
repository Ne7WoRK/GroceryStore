package com.cloudruid.groceries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@SpringBootApplication(scanBasePackages={"com.cloudruid.controllers","com.cloudruid.service"})
@EntityScan("com.cloudruid.model")
@EnableJpaRepositories("com.cloudruid.repository")
public class CloudruidApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CloudruidApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder app) {
		return app.sources(CloudruidApplication.class);
	}
}
