package com.example.PatientApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.PatientApp.model.MedicalRecords;
import com.example.PatientApp.model.Patient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.annotation.Backoff;

import javax.annotation.PostConstruct;

@Service
public class PatientService {

    @Lazy
    @Autowired
    RestTemplate restTemplate;

    private List<Patient> patients = new ArrayList<>();

    @PostConstruct
    public void init() {
        patients.add(new Patient(1L, "John Doe", "JohnDoe@gmail.com", "1234567890", "123 Main St"));
        patients.add(new Patient(2L, "Jane Doe", "JaneDoe@gmail.com", "0987654321", "456 Elm St"));
        patients.add(new Patient(3L, "Alice Smith", "AliceSmith@gmail.com", "1231231234", "789 Oak St"));
        patients.add(new Patient(4L, "Bob Smith", "BobSmith@gmail.com", "4564564567", "012 Pine St"));
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public Patient getPatient(Long id) {
        return patients.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void deletePatient(Long id) {
        patients.removeIf(p -> p.getId().equals(id));
    }

    public void modifyPatient(Long id, Patient patient) {
        patients.stream().filter(p -> p.getId().equals(id)).forEach(p -> {
            p.setName(patient.getName());
            p.setEmail(patient.getEmail());
            p.setPhone(patient.getPhone());
            p.setAddress(patient.getAddress());
        });
    }

    @HystrixCommand(fallbackMethod = "getMedicalRecordsFallback")
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<MedicalRecords> getMedicalRecords(long patientId) {
        return restTemplate.exchange(
                "http://localhost:8181/medicalRecords/patient/" + patientId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MedicalRecords>>() {
                }).getBody();
    }

    public String getMedicalRecordsFallback(long patientId) {
        return "Aucun dossier médical trouvé pour le patient " + patientId;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
