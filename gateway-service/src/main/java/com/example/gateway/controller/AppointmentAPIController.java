package com.example.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Retryable;

import com.example.gateway.model.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.retry.annotation.Backoff;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentAPIController {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "defaultPatient")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/patient/{id}")
    public Patient getPatient(@PathVariable long id) {
        return restTemplate.getForObject("http://appointment-api/appointments/patient/" + id, Patient.class);
    }

    public String defaultPatient(long id) {
        return "Aucun Patient n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultPractitioner")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/practitioner/{id}")
    public Practitioner getPractitioner(@PathVariable long id) {
        return restTemplate.getForObject("http://appointment-api/appointments/practitioner/" + id, Practitioner.class);
    }

    public String defaultPractitioner(long id) {
        return "Aucun Praticien n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultConsultations")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/consultations")
    public List<Consultation> getConsultations() {
        return restTemplate.exchange("http://appointment-api/appointments/consultations", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Consultation>>() {
                }).getBody();
    }

    public String defaultConsultations() {
        return "Aucune Consultation n'a été trouvée";
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @DeleteMapping("/consultation/{id}")
    public void deleteConsultation(@PathVariable long id) {
        restTemplate.delete("http://appointment-api/appointments/consultation/" + id);
    }

    public String defaultDeleteConsultation(long id) {
        return "La Consultation n'a pas pu être supprimée";
    }

    @HystrixCommand(fallbackMethod = "defaultAddConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @PostMapping("/consultation")
    public void addConsultation(@RequestBody Consultation consultation) {
        restTemplate.postForObject("http://appointment-api/appointments/consultation", consultation,
                Consultation.class);
    }

    public String defaultAddConsultation(Consultation consultation) {
        return "La Consultation n'a pas pu être ajoutée";
    }

    @HystrixCommand(fallbackMethod = "defaultUpdateConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @PutMapping("/consultation")
    public void updateConsultation(@RequestBody Consultation consultation) {
        restTemplate.put("http://appointment-api/appointments/consultation", consultation);
    }

    public String defaultUpdateConsultation(Consultation consultation) {
        return "La Consultation n'a pas pu être mise à jour";
    }

    @HystrixCommand(fallbackMethod = "defaultConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/consultation/{id}")
    public Consultation getConsultation(@PathVariable long id) {
        return restTemplate.getForObject("http://appointment-api/appointments/consultation/" + id, Consultation.class);
    }

    public String defaultConsultation(long id) {
        return "Aucune Consultation n'a été trouvée";
    }

    @HystrixCommand(fallbackMethod = "defaultCreateConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @PostMapping("/createConsultation")
    public void createConsultation(@RequestParam long patientId, @RequestParam long practitionerId,
            @RequestParam String date) {
        restTemplate.postForObject("http://appointment-api/appointments/createConsultation?patientId=" + patientId
                + "&practitionerId=" + practitionerId + "&date=" + date, null, Void.class);
    }

    public String defaultCreateConsultation(long patientId, long practitionerId, String date) {
        return "La Consultation n'a pas pu être créée";
    }

    @HystrixCommand(fallbackMethod = "defaultConsultForPractitionerId")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/consultations/practitioner/{practitionerId}")
    public List<String> consultForPractitionerId(@PathVariable long practitionerId) {
        return restTemplate.exchange("http://appointment-api/appointments/consultations/practitioner/" + practitionerId,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                }).getBody();
    }

    public String defaultConsultForPractitionerId(long practitionerId) {
        return "Aucune Consultation n'a été trouvée pour ce Praticien";
    }

    @HystrixCommand(fallbackMethod = "defaultConsultForPatientId")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/consultations/patient/{patientId}")
    public List<String> consultForPatientId(@PathVariable long patientId) {
        return restTemplate.exchange("http://appointment-api/appointments/consultations/patient/" + patientId,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                }).getBody();
    }

    public String defaultConsultForPatientId(long patientId) {
        return "Aucune Consultation n'a été trouvée pour ce Patient";
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
