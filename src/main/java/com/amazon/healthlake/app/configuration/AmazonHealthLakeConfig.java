package com.amazon.healthlake.app.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.healthlake.AmazonHealthLake;
import com.amazonaws.services.healthlake.AmazonHealthLakeClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Srushti.Chavan
 */
@Configuration
public class AmazonHealthLakeConfig {

    @Value("${aws.credentials.accessKey}")
    private String accessKey;

    @Value("${aws.credentials.secreteKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    /**
     * Configuration bean of amazon healthlake
     *
     * @return object of AmazonHealthLake
     */
    @Bean
    public AmazonHealthLake getAmazonHealthLake() {
        AWSCredentials awsCredentials =
                new BasicAWSCredentials(accessKey, secretKey);

        return AmazonHealthLakeClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
