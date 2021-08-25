package edu.kit.microservice_management.api.controller;

import edu.kit.microservice_management.logic.model.Microservice;
import edu.kit.microservice_management.logic.model.health.Health;
import edu.kit.microservice_management.logic.operations.MicroserviceOperations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MicroserviceController.class)
class MicroserviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MicroserviceOperations microserviceOperations;

    @Test
    void getAllMicroservicesShouldBeOk() throws Exception {
        Collection<Microservice> microservices = new ArrayList<>();
        microservices.add(
                new Microservice(
                        "hostname", "version", "address", "codeOwner",
                        new ArrayList<>(), new ArrayList<>(), new Health()
                )
        );
        Mockito.when(microserviceOperations.getListOfRegisteredMicroservices()).thenReturn(microservices);

        this.mockMvc.perform(get("/microservices")).andExpect(status().isOk()).andDo(print());
    }
}