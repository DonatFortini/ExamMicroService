package com.example.Practitioner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Practitioner.delegate.PractitionerServiceDelegate;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Practitioner", description = "REST API for Practitioner", tags = { "Practitioner" })
@RequestMapping("/practitioner")
public class PractitionerController {

    @Autowired
    PractitionerServiceDelegate studentServiceDelegate;
    // TODO finir auqnd j'ai fait rdv
}
