package com.amazon.healthlake.app.controller;

import com.amazon.healthlake.app.service.HealthLakeService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Srushti.Chavan
 */
@RestController
public class HealthLakeController {

    @Autowired
    private HealthLakeService healthLakeService;

    /**
     * Create Patient resource
     *
     * @param body
     * @return response of created resource
     */
    @PostMapping("/createResource")
    public ResponseEntity<JsonNode> CreatePatientResource(@RequestBody JsonNode body) {
        JsonNode response = healthLakeService.createPatientResource(body);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
