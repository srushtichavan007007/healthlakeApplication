package com.amazon.healthlake.app;

import com.amazon.healthlake.app.controller.HealthLakeController;
import com.amazon.healthlake.app.service.HealthLakeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HealthLakeApplicationTest {

    @Autowired
    private HealthLakeController healthLakeController;

    @Autowired
    private HealthLakeService healthLakeService;

    @Test
    void contextLoads() {
        assertThat(healthLakeController).isNotNull();
        assertThat(healthLakeService).isNotNull();
    }
}
