package com.example.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.gateway.model.Practitioner;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;

@RestController
@RequestMapping("/api/practitioner")
public class PractitionerAPIController {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "defaultPractitioners")
    @GetMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<Practitioner> getPractitioners() {
        return restTemplate.exchange("http://practitioner-api/practitioner", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Practitioner>>() {
                }).getBody();
    }

    public String defaultPractitioners() {
        return "Aucun Praticien n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @GetMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Practitioner getPractitioner(long id) {
        return restTemplate.getForObject("http://practitioner-api/practitioner/" + id, Practitioner.class);
    }

    public String defaultPractitioner(long id) {
        return "Aucun Praticien n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @PostMapping("/add")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Practitioner addPractitioner(Practitioner practitioner) {
        return restTemplate.postForObject("http://practitioner-api/practitioner/add", practitioner, Practitioner.class);
    }

    public String defaultPractitioner(Practitioner practitioner) {
        return "Aucun Praticien n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @PutMapping("/update/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Practitioner updatePractitioner(long id, Practitioner practitioner) {
        return restTemplate.postForObject("http://practitioner-api/practitioner/update/" + id, practitioner,
                Practitioner.class);
    }

    public String defaultPractitioner(long id, Practitioner practitioner) {
        return "Aucun Praticien n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @GetMapping("/delete/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deletePractitioner(long id) {
        restTemplate.delete("http://practitioner-api/practitioner/delete/" + id);
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @PostMapping("/addConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void addConsultation(long id, long idPatientS, String date) {
        restTemplate.postForObject("http://practitioner-api/practitioner/addConsultation/" + id + "/" + idPatientS + "/"
                + date, null, Practitioner.class);
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @DeleteMapping("/deleteConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deleteConsultation(long id, long idPatientS, String date) {
        restTemplate.delete("http://practitioner-api/practitioner/deleteConsultation/" + id + "/" + idPatientS + "/"
                + date);
    }

    // TODO : finir les methodes et modif dans les originaux

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
