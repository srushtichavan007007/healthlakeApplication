package com.amazon.healthlake.app.service;

import com.amazon.healthlake.app.configuration.externalServices.AmazonHealthLakeConnector;
import com.amazonaws.services.healthlake.AmazonHealthLake;
import com.amazonaws.services.healthlake.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Srushti.Chavan
 */
@Service
public class HealthLakeService {

    @Autowired
    private AmazonHealthLake amazonHealthLakeClient;

    @Autowired
    private AmazonHealthLakeConnector amazonHealthLakeConnector;

    /**
     * Create Patient resource record in already available healthlake datasource
     *
     * @param jsonNode
     * @return json response
     */
    public JsonNode createPatientResource(JsonNode jsonNode) {
        return amazonHealthLakeConnector.createPatientResource(jsonNode);
    }

    /**
     * Create new healthlake datasource
     *
     * @return
     */
    public CreateFHIRDatastoreResult createFHIRDatasource() {
        CreateFHIRDatastoreRequest createFHIRDatastoreRequest = new CreateFHIRDatastoreRequest()
                .withDatastoreName("TestDatastore123")
                .withDatastoreTypeVersion(FHIRVersion.R4)
                .withPreloadDataConfig(new PreloadDataConfig()
                        .withPreloadDataType(PreloadDataType.SYNTHEA));

        return amazonHealthLakeClient.createFHIRDatastore(createFHIRDatastoreRequest);
    }
}
