package com.example.appointment.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel(description = "Details about the Practitioner")
public class Practitioner {
    private long id;
    private String name;
    private String speciality;

    public Practitioner() {
    }

    public Practitioner(long id, String name, String speciality) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
    }

    @ApiModelProperty(notes = "The database generated Practitioner ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ApiModelProperty(notes = "The name of the Practitioner")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(notes = "The speciality of the Practitioner")
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "Practitioner [id=" + id + ", name=" + name + ", speciality=" + speciality + "]";
    }
}