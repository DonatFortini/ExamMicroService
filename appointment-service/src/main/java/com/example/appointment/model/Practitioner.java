package com.example.appointment.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel(description = "Details about the Practitioner")
public class Practitioner {
    @ApiModelProperty(notes = "The unique id of the practitioner")
    private long id;
    @ApiModelProperty(notes = "The name of the practitioner")
    private String name;
    @ApiModelProperty(notes = "The speciality of the practitioner")
    private String speciality;

    public Practitioner() {
    }

    public Practitioner(long id, String name, String speciality) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
