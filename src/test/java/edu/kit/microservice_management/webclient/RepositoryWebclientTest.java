package edu.kit.microservice_management.webclient;

import edu.kit.microservice_management.infrastructure.connectors.RepositoryCommunication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RepositoryWebclientTest {
    @Autowired RepositoryCommunication repositoryCommunication;

    @Test
    public void contextLoads() {
        assertThat(repositoryCommunication).isNotNull();
    }

}
