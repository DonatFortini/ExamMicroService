package com.example.gateway.controller;

import com.example.gateway.model.*;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentAPIController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${appointment.api.path}")
    private String APPOINTMENT_API_PATH;

    @HystrixCommand(fallbackMethod = "defaultConsultations")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/")
    public List<Consultation> getConsultations() {
        return restTemplate
                .exchange(
                        APPOINTMENT_API_PATH,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Consultation>>() {
                        })
                .getBody();
    }

    public String defaultConsultations() {
        return "Aucune Consultation n'a été trouvée";
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @DeleteMapping("/{id}")
    public void deleteConsultation(@PathVariable long id) {
        restTemplate.delete(
                APPOINTMENT_API_PATH + id);
    }

    public String defaultDeleteConsultation(long id) {
        return "La Consultation n'a pas pu être supprimée";
    }

    @HystrixCommand(fallbackMethod = "defaultAddConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @PostMapping("/")
    public void addConsultation(@RequestBody Consultation consultation) {
        restTemplate.postForObject(
                APPOINTMENT_API_PATH,
                consultation,
                Consultation.class);
    }

    public String defaultAddConsultation(Consultation consultation) {
        return "La Consultation n'a pas pu être ajoutée";
    }

    @HystrixCommand(fallbackMethod = "defaultUpdateConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @PutMapping("/")
    public void updateConsultation(@RequestBody Consultation consultation) {
        restTemplate.put(
                APPOINTMENT_API_PATH,
                consultation);
    }

    public String defaultUpdateConsultation(Consultation consultation) {
        return "La Consultation n'a pas pu être mise à jour";
    }

    @HystrixCommand(fallbackMethod = "defaultConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/{id}")
    public Consultation getConsultation(@PathVariable long id) {
        return restTemplate.getForObject(
                APPOINTMENT_API_PATH + id,
                Consultation.class);
    }

    public String defaultConsultation(long id) {
        return "Aucune Consultation n'a été trouvée";
    }

    @HystrixCommand(fallbackMethod = "defaultCreateConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @PostMapping("/create")
    public void createConsultation(
            @RequestParam long patientId,
            @RequestParam long practitionerId,
            @RequestParam String date) {
        restTemplate.postForObject(
                APPOINTMENT_API_PATH + "/create?patientId=" +
                        patientId +
                        "&practitionerId=" +
                        practitionerId +
                        "&date=" +
                        date,
                null,
                Void.class);
    }

    public String defaultCreateConsultation(
            long patientId,
            long practitionerId,
            String date) {
        return "La Consultation n'a pas pu être créée";
    }

    @HystrixCommand(fallbackMethod = "defaultConsultForPractitionerId")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/practitioner/{practitionerId}")
    public List<String> consultForPractitionerId(
            @PathVariable long practitionerId) {
        return restTemplate
                .exchange(
                        APPOINTMENT_API_PATH + "practitioner/" +
                                practitionerId,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<String>>() {
                        })
                .getBody();
    }

    public String defaultConsultForPractitionerId(long practitionerId) {
        return "Aucune Consultation n'a été trouvée pour ce Praticien";
    }

    @HystrixCommand(fallbackMethod = "defaultConsultForPatientId")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping("/patient/{patientId}")
    public List<String> consultForPatientId(@PathVariable long patientId) {
        return restTemplate
                .exchange(
                        APPOINTMENT_API_PATH + "patient/" +
                                patientId,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<String>>() {
                        })
                .getBody();
    }

    public String defaultConsultForPatientId(long patientId) {
        return "Aucune Consultation n'a été trouvée pour ce Patient";
    }

}
