package com.example.Practitioner.controller;

import com.example.Practitioner.delegate.PractitionerServiceDelegate;
import com.example.Practitioner.model.Consultation;
import com.example.Practitioner.model.MedicalRecords;
import com.example.Practitioner.model.Practitioner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Practitioner", description = "REST API for Practitioner", tags = { "Practitioner" })
@RequestMapping("/practitioners")
public class PractitionerController {

    @Autowired
    PractitionerServiceDelegate practitionerServiceDelegate;

    @ApiOperation(value = "Get all practitioners")
    @GetMapping("/")
    public List<Practitioner> getAllPractitioners() {
        return practitionerServiceDelegate.getAllPractitioners();
    }

    @ApiOperation(value = "Get practitioner by ID")
    @GetMapping("/{id}")
    public Practitioner getPractitionerById(@PathVariable long id) {
        return practitionerServiceDelegate.getPractitionerById(id);
    }

    @ApiOperation(value = "Add a new practitioner")
    @PostMapping("/")
    public Practitioner addPractitioner(
            @RequestBody Practitioner practitioner) {
        return practitionerServiceDelegate.addPractitioner(practitioner);
    }

    @ApiOperation(value = "Update an existing practitioner")
    @PutMapping("/{id}")
    public Practitioner updatePractitioner(
            @PathVariable long id,
            @RequestBody Practitioner practitioner) {
        return practitionerServiceDelegate.updatePractitioner(id, practitioner);
    }

    @ApiOperation(value = "Delete a practitioner")
    @DeleteMapping("/{id}")
    public void deletePractitioner(@PathVariable long id) {
        practitionerServiceDelegate.deletePractitioner(id);
    }

    @ApiOperation(value = "Add a consultation")
    @PostMapping("/consultation")
    public void addConsultation(
            @RequestParam long id,
            @RequestParam long idPatientS,
            @RequestParam String date) {
        practitionerServiceDelegate.addConsultation(id, idPatientS, date);
    }

    @ApiOperation(value = "Delete a consultation")
    @DeleteMapping("/consultation/{id}")
    public void deleteConsultation(@PathVariable long id) {
        practitionerServiceDelegate.deleteConsultation(id);
    }

    @ApiOperation(value = "Get consultations by practitioner ID")
    @GetMapping("/consultations/{id}")
    public List<Consultation> getConsultation(@PathVariable long id) {
        return practitionerServiceDelegate.getConsultations(id);
    }

    @ApiOperation(value = "Get medical records by practitioner ID")
    @GetMapping("/medicalRecords/{id}")
    public List<MedicalRecords> getMedicalRecords(@PathVariable long id) {
        return practitionerServiceDelegate.getMedicalRecords(id);
    }
}
