package com.amazon.healthlake.app.controller;

import com.amazon.healthlake.app.service.HealthLakeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthLakeController.class)
public class HealthLakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthLakeService healthLakeService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldSuccessfullyCreatePatientResource() throws Exception {
        //given
        when(healthLakeService.createPatientResource(isA(JsonNode.class))).thenReturn(aJsonResponse());

        //when
        this.mockMvc.perform(post("/createResource")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(aJsonRequest()))
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(asJsonString(aJsonResponse()))));

        //then
        verify(healthLakeService, times(1)).createPatientResource(isA(JsonNode.class));
        verifyNoMoreInteractions(healthLakeService);
    }

    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(jsonProcessingException);
        }
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
