package edu.kit.microservice_management.webclient;

import edu.kit.microservice_management.infrastructure.connectors.DomainServiceCommunication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class DomainServiceWebclientTest {
    @Autowired
    private DomainServiceCommunication domainServiceCommunication;

    @Test
    public void contextLoads() {
        assertThat(domainServiceCommunication).isNotNull();
    }
}
