package com.example.appointment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.appointment.model.Consultation;
import com.example.appointment.model.Patient;
import com.example.appointment.model.Practitioner;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.*;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;
import java.security.GeneralSecurityException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Service
public class AppointmentService {

    @Value("${patients.api.url}")
    private String PATIENT_SERVICE_URL;

    @Value("${practitioners.api.url}")
    private String PRACTITIONER_SERVICE_URL;

    @Value("${google.calendar.api.url}")
    private String GOOGLE_CALENDAR_API_URL;

    @Value("${google.calendar.api.key}")
    private String GOOGLE_CALENDAR_API_KEY;

    private List<Consultation> consultations = new ArrayList<>();

    @Lazy
    @Autowired
    RestTemplate restTemplate;

    @PostConstruct
    void init() {
        consultations.add(new Consultation(1, 1, 1, "2021-06-01"));
        consultations.add(new Consultation(2, 2, 2, "2021-06-02"));
        consultations.add(new Consultation(3, 3, 3, "2021-06-03"));
        consultations.add(new Consultation(4, 1, 2, "2021-06-04"));
    }

    @HystrixCommand(fallbackMethod = "fallbackGetPatient")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public Patient getPatient(long patientId) {
        return restTemplate.getForObject(PATIENT_SERVICE_URL + patientId, Patient.class);
    }

    public String fallbackGetPatient(long patientId) {
        return "Aucun patient trouvé";
    }

    @HystrixCommand(fallbackMethod = "fallbackGetPractitioner")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public Practitioner getPractitioner(long practitionerId) {
        return restTemplate.getForObject(PRACTITIONER_SERVICE_URL + practitionerId, Practitioner.class);
    }

    public String fallbackGetPractitioner(long practitionerId) {
        return "Aucun praticien trouvé";
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void deleteConsultation(long consultationId) {
        consultations.removeIf(c -> c.getId() == consultationId);
    }

    public void addConsultation(Consultation consultation) {
        consultations.add(consultation);
    }

    public void updateConsultation(Consultation consultation) {
        deleteConsultation(consultation.getId());
        addConsultation(consultation);
    }

    public Consultation getConsultation(long consultationId) {
        return consultations.stream().filter(c -> c.getId() == consultationId).findFirst().orElse(null);
    }

    @HystrixCommand(fallbackMethod = "fallbackCreateConsultation")
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void createConsultation(long patientId, long practitionerId, String date) {
        Patient patient = getPatient(patientId);
        Practitioner practitioner = getPractitioner(practitionerId);

        Event event = new Event()
                .setSummary("Consultation between " + patient.getName() + " and " + practitioner.getName())
                .setDescription("Consultation scheduled via Appointment Service");
        EventDateTime start = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(date))
                .setTimeZone("Europe/Paris");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(date))
                .setTimeZone("Europe/Paris");
        event.setEnd(end);

        try {
            Calendar service = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(), null)
                    .setApplicationName("Appointment Service")
                    .build();

            event = service.events().insert("primary", event).setKey(GOOGLE_CALENDAR_API_KEY).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public void fallbackCreateConsultation(long patientId, long practitionerId, String date) {
        System.out.println("Failed to create consultation. Please try again later.");
    }

    public List<Consultation> consultForPractitionerId(long practitionerId) {
        return consultations.stream().filter(c -> c.getPractitionerId() == practitionerId)
                .collect(Collectors.toList());
    }

    public List<Consultation> consultForPatientId(long patientId) {
        return consultations.stream().filter(c -> c.getPatientId() == patientId).collect(Collectors.toList());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
