package edu.kit.microservice_management.microservices;

import edu.kit.microservice_management.logic.model.services.DomainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DomainServiceTest {
    @Autowired
    private DomainService domainService;

    @Test
    public void contextLoads() {
        assertThat(domainService).isNotNull();
    }
}
