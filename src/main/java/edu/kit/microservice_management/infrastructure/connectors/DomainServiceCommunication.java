package edu.kit.microservice_management.infrastructure.connectors;

import edu.kit.microservice_management.infrastructure.configuration.Constants;
import edu.kit.microservice_management.infrastructure.utils.logging.Logger;
import edu.kit.microservice_management.logic.model.Microservice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class DomainServiceCommunication {
    private WebClient webClient;

    private DomainServiceCommunication(@Value("${domain.service.url}") String baseUrl) {
        DefaultUriBuilderFactory factoryWithoutEncoding = new DefaultUriBuilderFactory(baseUrl);
        factoryWithoutEncoding.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        this.webClient = WebClient.builder()
                .uriBuilderFactory(factoryWithoutEncoding)
                .baseUrl(baseUrl)
                .build();
    }

    public Collection<Microservice> getMicroservices() {
        return webClient.get()
                .uri("/microservice")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Collection<Microservice>>() {
                })
                .block();
    }


    public Microservice getSingleMicroservice(String deployment_name) {
        return webClient.get()
                .uri(uriBuilder ->
                    uriBuilder.
                        path("/microservice/{deployment_name}").
                        build(deployment_name)
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Microservice.class)
                .block();
    }

    public boolean sendMicroservice(String microserviceJson) {
        Logger.printMessage("Send to database:" + microserviceJson);
        try {
        String responce = webClient.post()
                .uri("/microservice")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(microserviceJson), String.class)
                .retrieve()
                .bodyToMono(String.class).block();
        Logger.printMessage("Send finished");
        } catch (WebClientException e) {
            return false;
        }
        return true;
    }

    public boolean checkMicroserviceInDatabase(Microservice microservice) {
        boolean isInDatabase;
        try {
            String response = webClient.get().uri(uriBuilder ->
                uriBuilder.
                    path("/microservice/{hostname}").
                    build(microservice.getHostname())).
                retrieve().
                bodyToMono(String.class).block();
            if (response == null || response.equals("null")) {
                Logger.printMessage("Microservice is not in database");
                isInDatabase = false;
            } else {
                Logger.printMessage("Microservice is in database");
                isInDatabase = true;
            }
        } catch (WebClientException e) {
            Logger.printError(Constants.CHECK_MICROSERVICE_IN_DATABASE_ERROR);
            isInDatabase = false;
        }
        return isInDatabase;
    }

    public boolean makeEmptyGet() {
        try {
            webClient.get().retrieve().bodyToMono(String.class).block();
            return true;
        } catch (WebClientException e) {
            return false;
        }
    }

    public void deleteMicroserviceByHostName(String hostName) {
        String response = webClient.delete().uri(uriBuilder ->
            uriBuilder.
                path("/microservice/{hostname}").
                build(hostName)).
            retrieve().
            bodyToMono(String.class).block();
    }
}
