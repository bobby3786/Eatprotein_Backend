package com.apputil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableDiscoveryClient
public class AppUtilApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppUtilApplication.class, args);
	}

}
