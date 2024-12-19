package com.example.appointment.model;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;

@Entity
@ApiModel(description = "RDV entre un patient et un médecin")
public class Consultation {
    private long id;
    private long patientId;
    private long practitionerId;

}
