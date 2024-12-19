package com.example.PatientApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.PatientApp.model.Patient;
import javax.annotation.PostConstruct;

@Service
public class PatientService {
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
}
