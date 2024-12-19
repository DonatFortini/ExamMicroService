package com.example.Practitioner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PractitionerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PractitionerApplication.class, args);
	}

}
