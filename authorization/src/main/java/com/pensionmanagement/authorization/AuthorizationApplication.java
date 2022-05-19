package com.pensionmanagement.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class AuthorizationApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		log.info("Application started");
		SpringApplication.run(AuthorizationApplication.class, args);
	}

}
