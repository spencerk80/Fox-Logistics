package com.github.spencerk.ReimbursementAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class ReimbursementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReimbursementApiApplication.class, args);
	}

}
