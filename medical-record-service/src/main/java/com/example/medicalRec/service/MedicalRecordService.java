package com.example.medicalRec.service;

import com.example.medicalRec.model.MedicalRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    private List<MedicalRecords> medicalRecords = new ArrayList<>();

    @PostConstruct
    public void init() {
        medicalRecords.add(new MedicalRecords(1L, 1L, new ArrayList<String>() {
            {
                add("Record 1");
                add("Record 2");
            }
        }));
        medicalRecords.add(new MedicalRecords(2L, 2L, new ArrayList<String>() {
            {
                add("Record 3");
                add("Record 4");
            }
        }));
        medicalRecords.add(new MedicalRecords(3L, 3L, new ArrayList<String>() {
            {
                add("Record 5");
                add("Record 6");
            }
        }));
    }

    public List<MedicalRecords> getMedicalRecordsForPatient(Long patientId) {
        return medicalRecords.stream().filter(medicalRecord -> medicalRecord.getPatientId() == patientId)
                .collect(Collectors.toList());
    }

    public List<MedicalRecords> getMedicalRecords() {
        return medicalRecords;
    }

    public List<MedicalRecords> getMedicalRecordsForPractitioner(Long practitionerId) {
        return medicalRecords.stream().filter(medicalRecord -> medicalRecord.getPractitionerId() == practitionerId)
                .collect(Collectors.toList());
    }

    public void addMedicalRecord(MedicalRecords medicalRecord) {
        medicalRecords.add(medicalRecord);
    }

    public void updateMedicalRecord(MedicalRecords medicalRecord) {
        medicalRecords.stream().filter(mr -> mr.getPatientId() == medicalRecord.getPatientId() &&
                mr.getPractitionerId() == medicalRecord.getPractitionerId())
                .forEach(mr -> {
                    mr.setMedicalRecords(medicalRecord.getMedicalRecords());
                });
    }

    public void deleteMedicalRecord(Long patientId, Long practitionerId) {
        medicalRecords.removeIf(medicalRecord -> medicalRecord.getPatientId() == patientId &&
                medicalRecord.getPractitionerId() == practitionerId);
    }

    public void createMedicalRecord(long patientId, long practitionerId, List<String> records) {
        medicalRecords.add(new MedicalRecords(patientId, practitionerId, records));
    }
}
