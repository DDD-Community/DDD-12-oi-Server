package com.ddd.oi;

import com.ddd.oi.auth.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableConfigurationProperties(CorsProperties.class)
@EnableJpaAuditing
@EnableScheduling
public class OiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OiApplication.class, args);
	}

}
