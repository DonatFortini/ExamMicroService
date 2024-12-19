package com.example.medicalRec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MedicalRecordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalRecordsApplication.class, args);
	}

}
