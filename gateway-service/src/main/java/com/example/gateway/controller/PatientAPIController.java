package com.example.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.gateway.model.Patient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpMethod;

import org.springframework.retry.annotation.Backoff;

@RestController
@RequestMapping("/api/patient")
public class PatientAPIController {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "defaultPatients")
    @GetMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<Patient> getPatients() {
        return restTemplate.exchange("http://patient-api/patients", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Patient>>() {
                }).getBody();
    }

    public String defaultPatients() {
        return "Aucun Patient n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPatient")
    @GetMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Patient getPatient(@PathVariable long id) {
        return restTemplate.getForObject("http://patient-api/patients/" + id, Patient.class);
    }

    public String defaultPatient(long id) {
        return "Aucun Patient n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPatient")
    @PostMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Patient addPatient(@RequestBody Patient patient) {
        return restTemplate.postForObject("http://patient-api/patients", patient, Patient.class);
    }

    @HystrixCommand(fallbackMethod = "defaultPatient")
    @PutMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Patient updatePatient(@PathVariable long id, @RequestBody Patient patient) {
        restTemplate.put("http://patient-api/patients/" + id, patient);
        return restTemplate.getForObject("http://patient-api/patients/" + id, Patient.class);
    }

    @HystrixCommand(fallbackMethod = "defaultPatient")
    @DeleteMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deletePatient(@PathVariable long id) {
        restTemplate.delete("http://patient-api/patients/" + id);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
