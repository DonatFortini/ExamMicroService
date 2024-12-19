package com.example.PatientApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PatientApp.model.Patient;
import com.example.PatientApp.service.PatientService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/patients")
@Api(value = "Systèmes de gestion de patient", description = "Opérations CRUD pour les patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    @ApiOperation(value = "Retourne tous les patient", response = List.class)
    @GetMapping("/")
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @ApiOperation(value = "Retourne un patient par ID", response = Patient.class)
    @GetMapping("/{id}")
    public Patient getPatient(@PathVariable Long id) {
        return patientService.getPatient(id);
    }

    @ApiOperation(value = "Ajoute un patient")
    @PostMapping("/")
    public void addPatient(Patient patient) {
        patientService.addPatient(patient);
    }

    @ApiOperation(value = "Supprime un patient")
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    @ApiOperation(value = "Modifie un patient")
    @PutMapping("/{id}")
    public void modifyPatient(@PathVariable Long id, Patient patient) {
        patientService.modifyPatient(id, patient);
    }

}
