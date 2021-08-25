package edu.kit.microservice_management.controller;

import edu.kit.microservice_management.api.controller.DefaultController;
import edu.kit.microservice_management.infrastructure.configuration.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DefaultControllerTest {
    @Autowired
    private DefaultController defaultController;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void contextLoads() {
        assertThat(defaultController).isNotNull();
    }

    @Test
    public void emptyGetShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
            String.class)).contains(Constants.DEFAULT_HTTP_GET_ANSWER);
    }
}
