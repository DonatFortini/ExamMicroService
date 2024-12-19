package com.example.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.appointment.service.AppointmentService;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.appointment.model.*;

@RestController
@RequestMapping("/appointments")
@Api(value = "Appointment Management System", description = "Permet de gérer les rendez-vous entre les patients et les médecins")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/patient/{id}")
    public Patient getPatient(@PathVariable long id) {
        return appointmentService.getPatient(id);
    }

    @GetMapping("/practitioner/{id}")
    public Practitioner getPractitioner(@PathVariable long id) {
        return appointmentService.getPractitioner(id);
    }

    @GetMapping("/consultations")
    public List<Consultation> getConsultations() {
        return appointmentService.getConsultations();
    }

    @DeleteMapping("/consultation/{id}")
    public void deleteConsultation(@PathVariable long id) {
        appointmentService.deleteConsultation(id);
    }

    @PostMapping("/consultation")
    public void addConsultation(@RequestBody Consultation consultation) {
        appointmentService.addConsultation(consultation);
    }

    @PutMapping("/consultation")
    public void updateConsultation(@RequestBody Consultation consultation) {
        appointmentService.updateConsultation(consultation);
    }

    @GetMapping("/consultation/{id}")
    public Consultation getConsultation(@PathVariable long id) {
        return appointmentService.getConsultation(id);
    }

    @PostMapping("/createConsultation")
    public void createConsultation(@RequestParam long patientId, @RequestParam long practitionerId,
            @RequestParam String date) {
        appointmentService.createConsultation(patientId, practitionerId, date);
    }

}
