package com.example.medicalRec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.medicalRec.model.MedicalRecords;
import com.example.medicalRec.service.MedicalRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/medicalRecords")
@Api(value = "Medical Records", description = "REST API for Medical Records", tags = { "Medical Records" })
public class MedicalRecordsController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @ApiOperation(value = "Get all medical records")
    @GetMapping("/")
    public List<MedicalRecords> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }

    @ApiOperation(value = "Get medical records for a specific patient")
    @GetMapping("/patient/{patientId}")
    public List<MedicalRecords> getMedicalRecordsForPatient(
            @ApiParam(value = "ID of the patient", required = true) @PathVariable Long patientId) {
        return medicalRecordService.getMedicalRecordsForPatient(patientId);
    }

    @ApiOperation(value = "Get medical records for a specific practitioner")
    @GetMapping("/practitioner/{practitionerId}")
    public List<MedicalRecords> getMedicalRecordsForPractitioner(
            @ApiParam(value = "ID of the practitioner", required = true) @PathVariable Long practitionerId) {
        return medicalRecordService.getMedicalRecordsForPractitioner(practitionerId);
    }

    @ApiOperation(value = "Add a new medical record")
    @PostMapping("/")
    public void addMedicalRecord(
            @ApiParam(value = "Medical record to be added", required = true) @RequestBody MedicalRecords medicalRecord) {
        medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @ApiOperation(value = "Update an existing medical record")
    @PutMapping("/")
    public void updateMedicalRecord(
            @ApiParam(value = "Medical record to be updated", required = true) @RequestBody MedicalRecords medicalRecord) {
        medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @ApiOperation(value = "Delete a medical record for a specific patient and practitioner")
    @DeleteMapping("/patient/{patientId}/practitioner/{practitionerId}")
    public void deleteMedicalRecord(
            @ApiParam(value = "ID of the patient", required = true) @PathVariable Long patientId,
            @ApiParam(value = "ID of the practitioner", required = true) @PathVariable Long practitionerId) {
        medicalRecordService.deleteMedicalRecord(patientId, practitionerId);
    }

    @ApiOperation(value = "Create a new medical record with multiple records")
    @PostMapping("/create")
    public void createMedicalRecord(
            @ApiParam(value = "ID of the patient", required = true) @RequestParam long patientId,
            @ApiParam(value = "ID of the practitioner", required = true) @RequestParam long practitionerId,
            @ApiParam(value = "List of records", required = true) @RequestBody List<String> records) {
        medicalRecordService.createMedicalRecord(patientId, practitionerId, records);
    }
}
