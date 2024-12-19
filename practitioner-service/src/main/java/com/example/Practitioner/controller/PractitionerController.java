package com.example.Practitioner.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Practitioner.delegate.PractitionerServiceDelegate;
import com.example.Practitioner.model.Practitioner;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Api(value = "Practitioner", description = "REST API for Practitioner", tags = { "Practitioner" })
@RequestMapping("/practitioner")
public class PractitionerController {

    @Autowired
    PractitionerServiceDelegate practitionerServiceDelegate;

    @GetMapping("/all")
    public List<Practitioner> getAllPractitioners() {
        return practitionerServiceDelegate.getAllPractitioners();
    }

    @GetMapping("/{id}")
    public Practitioner getPractitionerById(@PathVariable long id) {
        return practitionerServiceDelegate.getPractitionerById(id);
    }

    @PostMapping("/add")
    public Practitioner addPractitioner(@RequestBody Practitioner practitioner) {
        return practitionerServiceDelegate.addPractitioner(practitioner);
    }

    @PutMapping("/update/{id}")
    public Practitioner updatePractitioner(@PathVariable long id, @RequestBody Practitioner practitioner) {
        return practitionerServiceDelegate.updatePractitioner(id, practitioner);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePractitioner(@PathVariable long id) {
        practitionerServiceDelegate.deletePractitioner(id);
    }

    @PostMapping("/addConsultation")
    public void addConsultation(@RequestParam long id, @RequestParam long idPatientS, @RequestParam String date) {
        practitionerServiceDelegate.addConsultation(id, idPatientS, date);
    }

    @GetMapping("/consultations/{id}")
    public List<String> getConsultation(@PathVariable long id) {
        return practitionerServiceDelegate.getConsultation(id);
    }

}
