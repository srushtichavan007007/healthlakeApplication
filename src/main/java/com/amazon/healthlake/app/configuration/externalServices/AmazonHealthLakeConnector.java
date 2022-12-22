package com.amazon.healthlake.app.configuration.externalServices;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Srushti.Chavan
 */
@Configuration
public class AmazonHealthLakeConnector {

    WebClient client;
    Environment environment;

    public AmazonHealthLakeConnector(Environment environment, @Value("${aws.healthlake.base.url}") String healthLakeBaseUrl) {
        this.environment = environment;
        this.client = WebClient.create(healthLakeBaseUrl);
    }

    /**
     * Call healthlake POST api to inject Patient resource in healthlake
     *
     * @param jsonNode
     * @return response of api
     */
    public JsonNode createPatientResource(JsonNode jsonNode) {
        try {
            return client.post().uri("Patient")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(jsonNode), JsonNode.class)
                    .exchangeToMono(response ->
                            response.bodyToMono(JsonNode.class))
                    .block();
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }
}
