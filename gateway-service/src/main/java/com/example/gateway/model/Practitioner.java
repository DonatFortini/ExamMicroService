package com.example.gateway.model;

import javax.persistence.Entity;

@Entity
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
