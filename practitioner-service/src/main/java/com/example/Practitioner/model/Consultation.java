package com.example.Practitioner.model;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "RDV entre un patient et un médecin")
public class Consultation {
    @ApiModelProperty(notes = "Identifiant unique de la consultation")
    private long id;
    @ApiModelProperty(notes = "Identifiant du patient")
    private long patientId;
    @ApiModelProperty(notes = "Identifiant du médecin")
    private long practitionerId;
    @ApiModelProperty(notes = "Date de la consultation")
    private String date;

    public Consultation() {
    }

    public Consultation(long id, long patientId, long practitionerId, String date) {
        this.id = id;
        this.patientId = patientId;
        this.practitionerId = practitionerId;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Consultation [id=" + id + ", patientId=" + patientId + ", practitionerId=" + practitionerId + ", date="
                + date + "]";
    }

}
