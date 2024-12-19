package com.example.Practitioner.delegate;

import com.example.Practitioner.model.Consultation;
import com.example.Practitioner.model.MedicalRecords;
import com.example.Practitioner.model.Practitioner;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PractitionerServiceDelegate {

    @Lazy
    @Autowired
    RestTemplate restTemplate;

    private List<Practitioner> practitioners = new ArrayList<>();

    @PostConstruct
    public void init() {
        practitioners.add(new Practitioner(1, "Dr. John Doe", "Dentist"));
        practitioners.add(
                new Practitioner(2, "Dr. Jane Smith", "General Physician"));
        practitioners.add(
                new Practitioner(3, "Dr. Chuck Berry", "Cardiologist"));
        practitioners.add(
                new Practitioner(4, "Dr. Stu Pendous", "Neurologist"));
        practitioners.add(
                new Practitioner(5, "Dr. Mike Rosoft", "Psychiatrist"));
    }

    public List<Practitioner> getAllPractitioners() {
        return practitioners;
    }

    public Practitioner getPractitionerById(long id) {
        return practitioners
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Practitioner addPractitioner(Practitioner practitioner) {
        practitioners.add(practitioner);
        return practitioner;
    }

    public Practitioner updatePractitioner(long id, Practitioner practitioner) {
        Practitioner existingPractitioner = practitioners
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (existingPractitioner != null) {
            existingPractitioner.setName(practitioner.getName());
            existingPractitioner.setSpeciality(practitioner.getSpeciality());
        }
        return existingPractitioner;
    }

    public void deletePractitioner(long id) {
        practitioners.removeIf(p -> p.getId() == id);
    }

    @HystrixCommand(fallbackMethod = "getConsultationFallback")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void addConsultation(long id, long idPatientS, String date) {
        restTemplate.postForObject(
                "http://localhost:8095/appointments/create?patientId=" +
                        idPatientS +
                        "&practitionerId=" +
                        id +
                        "&date=" +
                        date,
                null,
                String.class);
    }

    @HystrixCommand(fallbackMethod = "getConsultationFallback")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void deleteConsultation(long id) {
        restTemplate.delete(
                "http://localhost:8095/appointments/" + id);
    }

    @HystrixCommand(fallbackMethod = "getConsultationFallback")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<Consultation> getConsultations(long id) {
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        return restTemplate
                .exchange(
                        "http://localhost:8095/appointments/practitioner/" +
                                id,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<Consultation>>() {
                        })
                .getBody();
    }

    public String getConsultationFallback(long id) {
        return "No consultation found for practitioner with id " + id;
    }

    public String getConsultationFallback(
            long id,
            long idPatientS,
            String date) {
        return "No consultation found for practitioner with id " + id;
    }

    @HystrixCommand(fallbackMethod = "getMedicalRecordsFallback")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public List<MedicalRecords> getMedicalRecords(long practitionerId) {
        HttpEntity<String> entity = new HttpEntity<>(null, null);
        return restTemplate
                .exchange(
                        "http://localhost:8095/medicalRecords/practitioner/" +
                                practitionerId,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<MedicalRecords>>() {
                        })
                .getBody();
    }

    public String getMedicalRecordsFallback(long practitionerId) {
        return "Aucun dossier medical associé au practitien  " + practitionerId;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
