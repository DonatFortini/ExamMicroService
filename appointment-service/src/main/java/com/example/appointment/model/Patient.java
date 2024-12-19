package com.example.appointment.model;

import javax.persistence.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "Détails du patient")
public class Patient {

    public Patient() {
    }

    public Patient(Long id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @ApiModelProperty(notes = "ID du patient")
    private Long id;

    @ApiModelProperty(notes = "Nom du patient")
    private String name;

    @ApiModelProperty(notes = "Email du patient")
    private String email;

    @ApiModelProperty(notes = "Numéro de téléphone du patient")
    private String phone;

    @ApiModelProperty(notes = "Adresse du patient")
    private String address;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient [address=" + address + ", email=" + email + ", id=" + id + ", name=" + name + ", phone=" + phone
                + "]";
    }
}