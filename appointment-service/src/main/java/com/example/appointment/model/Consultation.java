package com.example.appointment.model;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "RDV entre un patient et un médecin")
public class Consultation {
    private long id;
    private Patient patient;
    private Practitioner practitioner;
    private String date;

    public Consultation() {
    }

    public Consultation(long id, Patient patient, Practitioner practitioner, String date) {
        this.id = id;
        this.patient = patient;
        this.practitioner = practitioner;
        this.date = date;
    }

    @ApiModelProperty(notes = "ID de la consultation")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "Patient de la consultation")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ApiModelProperty(notes = "Médecin de la consultation")
    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    @ApiModelProperty(notes = "Date de la consultation")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Consultation entre le patient " + patient.getName() + " et le médecin " + practitioner.getName()
                + " le " + date;
    }

}
