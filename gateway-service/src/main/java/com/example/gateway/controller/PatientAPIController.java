package com.example.gateway.controller;

import com.example.gateway.model.MedicalRecords;
import com.example.gateway.model.Patient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/patient")
public class PatientAPIController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${patient.api.path}")
    private String PATIENT_API_PATH;

    @HystrixCommand(fallbackMethod = "defaultPatients")
    @GetMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<Patient> getPatients() {
        return restTemplate
                .exchange(
                        PATIENT_API_PATH,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Patient>>() {
                        })
                .getBody();
    }

    public List<Patient> defaultPatients() {
        return Arrays.asList(new Patient());
    }

    @HystrixCommand(fallbackMethod = "defaultPatient")
    @GetMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Patient getPatient(@PathVariable long id) {
        return restTemplate.getForObject(
                PATIENT_API_PATH + id,
                Patient.class);
    }

    public Patient defaultPatient(long id) {
        return new Patient();
    }

    @HystrixCommand(fallbackMethod = "defaultPatient")
    @PostMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Patient addPatient(@RequestBody Patient patient) {
        return restTemplate.postForObject(
                PATIENT_API_PATH,
                patient,
                Patient.class);
    }

    @HystrixCommand(fallbackMethod = "defaultPatient")
    @PutMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public Patient updatePatient(
            @PathVariable long id,
            @RequestBody Patient patient) {
        restTemplate.put(PATIENT_API_PATH + id, patient);
        return restTemplate.getForObject(
                PATIENT_API_PATH + id,
                Patient.class);
    }

    @HystrixCommand(fallbackMethod = "defaultVoid")
    @DeleteMapping("/{id}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deletePatient(@PathVariable long id) {
        restTemplate.delete(PATIENT_API_PATH + id);
    }

    public String defaultVoid(long id) {
        return "Aucun Patient n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultMedicalRecords")
    @GetMapping("/{id}/medicalRecords")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<MedicalRecords> getMedicalRecords(@PathVariable long id) {
        return restTemplate
                .exchange(
                        PATIENT_API_PATH + id + "/medicalRecords",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<MedicalRecords>>() {
                        })
                .getBody();
    }

    public List<MedicalRecords> defaultMedicalRecords(long id) {
        return Arrays.asList(new MedicalRecords());
    }

}
