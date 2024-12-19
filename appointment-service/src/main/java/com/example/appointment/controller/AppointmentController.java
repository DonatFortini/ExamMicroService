package com.example.appointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.appointment.service.AppointmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.appointment.model.*;

@RestController
@RequestMapping("/appointments")
@Api(value = "Appointment Management System", description = "Permet de gérer les rendez-vous entre les patients et les médecins")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @ApiOperation(value = "Get all consultations")
    @GetMapping("/")
    public List<Consultation> getConsultations() {
        return appointmentService.getConsultations();
    }

    @ApiOperation(value = "Delete consultation by ID")
    @DeleteMapping("/{id}")
    public void deleteConsultation(@PathVariable long id) {
        appointmentService.deleteConsultation(id);
    }

    @ApiOperation(value = "Add a new consultation")
    @PostMapping("/")
    public void addConsultation(@RequestBody Consultation consultation) {
        appointmentService.addConsultation(consultation);
    }

    @ApiOperation(value = "Update an existing consultation")
    @PutMapping("/")
    public void updateConsultation(@RequestBody Consultation consultation) {
        appointmentService.updateConsultation(consultation);
    }

    @ApiOperation(value = "Get consultation by ID")
    @GetMapping("/{id}")
    public Consultation getConsultation(@PathVariable long id) {
        return appointmentService.getConsultation(id);
    }

    @ApiOperation(value = "Create a new consultation")
    @PostMapping("/create")
    public void createConsultation(@RequestParam long patientId, @RequestParam long practitionerId,
            @RequestParam String date) {
        appointmentService.createConsultation(patientId, practitionerId, date);
    }

    @ApiOperation(value = "Get consultations for a specific practitioner ID")
    @GetMapping("/practitioner/{practitionerId}")
    public List<Consultation> consultForPractitionerId(@PathVariable long practitionerId) {
        return appointmentService.consultForPractitionerId(practitionerId);
    }

    @ApiOperation(value = "Get consultations for a specific patient ID")
    @GetMapping("/patient/{patientId}")
    public List<Consultation> consultForPatientId(@PathVariable long patientId) {
        return appointmentService.consultForPatientId(patientId);
    }

}
