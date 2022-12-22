package com.amazon.healthlake.app.service;

import com.amazon.healthlake.app.configuration.externalServices.AmazonHealthLakeConnector;
import com.amazonaws.services.healthlake.AmazonHealthLake;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class HealthLakeServiceTest {

    @InjectMocks
    private HealthLakeService healthLakeService;

    @Mock
    private AmazonHealthLake amazonHealthLakeClient;

    @Mock
    private AmazonHealthLakeConnector amazonHealthLakeConnector;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldCreatePatientResource() throws Exception{
        //given
        when(amazonHealthLakeConnector.createPatientResource(isA(JsonNode.class))).thenReturn(aJsonResponse());

        //when
         JsonNode response = healthLakeService.createPatientResource(aJsonRequest());

        //then
        verify(amazonHealthLakeConnector, times(1)).createPatientResource(isA(JsonNode.class));
        verifyNoMoreInteractions(amazonHealthLakeConnector);
    }

    private JsonNode aJsonRequest() {
        try {
            String json = "{\n" +
                    "  \"resourceType\": \"Patient\",\n" +
                    "  \"identifier\": [ { \"system\": \"urn:oid:1.2.36.146.595.217.0.1\", \"value\": \"12345\" } ],\n" +
                    "  \"name\": [ {\n" +
                    "      \"family\": \"Silva\",\n" +
                    "      \"given\": [\"Ana\", \"Carolina\"]\n" +
                    "  } ],\n" +
                    "  \"gender\": \"female\",\n" +
                    "  \"birthDate\": \"1992-02-10\"\n" +
                    "}";
            return mapper.readTree(json);
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }

    private JsonNode aJsonResponse() {
        try {
            String json = "{\n" +
                    "  \"resourceType\": \"Patient\",\n" +
                    "  \"identifier\": [\n" +
                    "    {\n" +
                    "      \"system\": \"urn:oid:1.2.36.146.595.217.0.1\",\n" +
                    "      \"value\": \"12345\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"name\": [\n" +
                    "    {\n" +
                    "      \"family\": \"Silva\",\n" +
                    "      \"given\": [\n" +
                    "        \"Ana\",\n" +
                    "        \"Carolina\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"gender\": \"female\",\n" +
                    "  \"birthDate\": \"1992-02-10\",\n" +
                    "  \"id\": \"274b408a-1201-4e9f-a621-1df937f1a26d\",\n" +
                    "  \"meta\": {\n" +
                    "    \"lastUpdated\": \"2022-06-13T23:31:24.427Z\"\n" +
                    "  }\n" +
                    "}";
            return mapper.readTree(json);
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }
}
