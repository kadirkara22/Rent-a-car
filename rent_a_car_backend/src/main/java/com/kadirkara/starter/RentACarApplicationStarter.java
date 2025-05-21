package com.kadirkara.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.kadirkara"})
@EntityScan(basePackages = {"com.kadirkara"})
@EnableJpaRepositories(basePackages = {"com.kadirkara"})
@SpringBootApplication
public class RentACarApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(RentACarApplicationStarter.class, args);
	}

}
