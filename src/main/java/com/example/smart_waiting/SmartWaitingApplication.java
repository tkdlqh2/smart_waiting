package com.example.smart_waiting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SmartWaitingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartWaitingApplication.class, args);
	}

}
