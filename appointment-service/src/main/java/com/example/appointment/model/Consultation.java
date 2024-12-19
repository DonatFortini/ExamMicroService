package com.example.appointment.model;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "RDV entre un patient et un médecin")
public class Consultation {
    private long id;
    private long patientId;
    private long practitionerId;
    private String date;

    public Consultation() {
    }

    public Consultation(long id, long patientId, long practitionerId, String date) {
        this.id = id;
        this.patientId = patientId;
        this.practitionerId = practitionerId;
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
    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    @ApiModelProperty(notes = "Médecin de la consultation")
    public long getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(long practitionerId) {
        this.practitionerId = practitionerId;
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
        return "Consultation [id=" + id + ", patientId=" + patientId + ", practitionerId=" + practitionerId + ", date="
                + date + "]";
    }

}
