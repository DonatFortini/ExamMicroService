package com.example.Practitioner.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@ApiModel(description = "Details about the Practitioner")
public class Practitioner {
    @ApiModelProperty(notes = "The database generated practitioner ID")
    private long id;
    @ApiModelProperty(notes = "The practitioner name")
    private String name;
    @ApiModelProperty(notes = "The practitioner speciality")
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
