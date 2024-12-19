package com.example.gateway.controller;

import com.example.gateway.model.Practitioner;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/practitioner")
public class PractitionerAPIController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${practitioner.api.path}")
    private String PRACTITIONER_API_PATH;

    @HystrixCommand(fallbackMethod = "defaultPractitioners")
    @GetMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<Practitioner> getPractitioners() {
        return restTemplate
                .exchange(
                        PRACTITIONER_API_PATH,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Practitioner>>() {
                        })
                .getBody();
    }

    public String defaultPractitioners() {
        return "Aucun Praticien n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @GetMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Practitioner getPractitioner(long id) {
        return restTemplate.getForObject(
                PRACTITIONER_API_PATH + id,
                Practitioner.class);
    }

    public String defaultPractitioner(long id) {
        return "Aucun Praticien n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @PostMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Practitioner addPractitioner(Practitioner practitioner) {
        return restTemplate.postForObject(
                PRACTITIONER_API_PATH,
                practitioner,
                Practitioner.class);
    }

    public String defaultPractitioner(Practitioner practitioner) {
        return "Aucun Praticien n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @PutMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Practitioner updatePractitioner(long id, Practitioner practitioner) {
        return restTemplate.postForObject(
                PRACTITIONER_API_PATH + id,
                practitioner,
                Practitioner.class);
    }

    public String defaultPractitioner(long id, Practitioner practitioner) {
        return "Aucun Praticien n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @DeleteMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deletePractitioner(long id) {
        restTemplate.delete(
                PRACTITIONER_API_PATH + id);
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @PostMapping("/consultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void addConsultation(long id, long idPatientS, String date) {
        restTemplate.postForObject(
                PRACTITIONER_API_PATH +
                        "consultation/" +
                        id +
                        "/" +
                        idPatientS +
                        "/" +
                        date,
                null,
                Practitioner.class);
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @DeleteMapping("/consultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deleteConsultation(long id, long idPatientS, String date) {
        restTemplate.delete(
                PRACTITIONER_API_PATH +
                        "consultation/" +
                        id +
                        "/" +
                        idPatientS +
                        "/" +
                        date);
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @GetMapping("/consultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<String> getConsultation(long id, long idPatientS, String date) {
        return restTemplate
                .exchange(
                        PRACTITIONER_API_PATH +
                                "consultation/" +
                                id +
                                "/" +
                                idPatientS +
                                "/" +
                                date,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<String>>() {
                        })
                .getBody();
    }

}
