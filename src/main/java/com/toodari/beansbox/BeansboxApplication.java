package com.toodari.beansbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeansboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeansboxApplication.class, args);
	}

}