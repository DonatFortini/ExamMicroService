package com.example.Practitioner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEurekaClient
@SpringBootApplication
public class PractitionerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PractitionerApplication.class, args);
	}

}
