package com.example.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.example.gateway.model.MedicalRecords;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@RestController
@RequestMapping("/api/medicalrecs")
public class MedicalRecsAPIController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${medicalrecs.api.path}")
    private String MEDICALRECS_API_PATH;

    @HystrixCommand(fallbackMethod = "defaultMedicalRecs")
    @GetMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<MedicalRecords> getMedicalRecs() {
        return restTemplate
                .exchange(
                        MEDICALRECS_API_PATH,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<MedicalRecords>>() {
                        })
                .getBody();
    }

    public String defaultMedicalRecs() {
        return "Aucun Dossier Médical n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultMedicalRec")
    @GetMapping("/patient/{patientId}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<MedicalRecords> getMedicalRec(@PathVariable long patientId) {
        return restTemplate
                .exchange(
                        MEDICALRECS_API_PATH + "patient/" + patientId,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<MedicalRecords>>() {
                        })
                .getBody();
    }

    public String defaultMedicalRec(long patientId) {
        return "Aucun Dossier Médical n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultMedicalRec")
    @GetMapping("/practitioner/{practitionerId}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<MedicalRecords> getMedicalRecs(@PathVariable long practitionerId) {
        return restTemplate
                .exchange(
                        MEDICALRECS_API_PATH + "practitioner/" + practitionerId,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<MedicalRecords>>() {
                        })
                .getBody();
    }

    public String defaultMedicalRecs(long practitionerId) {
        return "Aucun Dossier Médical n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultMedicalRec")
    @PostMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public MedicalRecords addMedicalRec(@PathVariable MedicalRecords medicalRec) {
        return restTemplate.postForObject(
                MEDICALRECS_API_PATH,
                medicalRec,
                MedicalRecords.class);
    }

    public String defaultMedicalRec(MedicalRecords medicalRec) {
        return "Aucun Dossier Médical n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultMedicalRec")
    @PutMapping("/")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public MedicalRecords updateMedicalRec(@PathVariable MedicalRecords medicalRec) {
        return restTemplate.postForObject(
                MEDICALRECS_API_PATH,
                medicalRec,
                MedicalRecords.class);
    }

    @HystrixCommand(fallbackMethod = "defaultMedicalRec")
    @DeleteMapping("/patient/{patientId}/practitioner/{practitionerId}")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deleteMedicalRec(@PathVariable long patientId, @PathVariable long practitionerId) {
        restTemplate.delete(MEDICALRECS_API_PATH + "patient/" + patientId + "/practitioner/" + practitionerId);
    }

    public String defaultMedicalRec(long patientId, long practitionerId) {
        return "Aucun Dossier Médical n'a été trouvé";
    }

    @HystrixCommand(fallbackMethod = "defaultMedicalRec")
    @PostMapping("/create")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void createMedicalRec(
            @RequestParam long patientId,
            @RequestParam long practitionerId,
            @RequestParam List<String> records) {
        restTemplate.postForObject(
                MEDICALRECS_API_PATH + "create?patientId=" +
                        patientId +
                        "&practitionerId=" +
                        practitionerId +
                        "&records=" +
                        records,
                null,
                Void.class);
    }
}
