package com.example.gateway.model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class MedicalRecords {
    private long patientId;
    private long practitionerId;
    private List<String> medicalRecords;

    public MedicalRecords() {
    }

    public MedicalRecords(long patientId, long practitionerId, List<String> medicalRecords) {
        this.patientId = patientId;
        this.practitionerId = practitionerId;
        this.medicalRecords = medicalRecords;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(long practitionerId) {
        this.practitionerId = practitionerId;
    }

    public List<String> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<String> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    @Override
    public String toString() {
        return "MedicalRecords [medicalRecords=" + medicalRecords + ", patientId=" + patientId + ", practitionerId="
                + practitionerId + "]";
    }

}
