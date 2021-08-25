package edu.kit.microservice_management.infrastructure.connectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Component
public class PrometheusCommunication {
    private WebClient webClient;
    private ObjectMapper objectMapper;

    private PrometheusCommunication(@Value("${prometheus.url}") String baseUrl) {
        DefaultUriBuilderFactory factoryWithoutEncoding = new DefaultUriBuilderFactory(baseUrl);
        factoryWithoutEncoding.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        this.webClient = WebClient.builder()
                .uriBuilderFactory(factoryWithoutEncoding)
                .baseUrl(baseUrl)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode query(String query) throws JsonProcessingException {
        String json = webClient.get()
                .uri("/api/v1/query?query="+ URLEncoder.encode(query, StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return objectMapper.readTree(json);
    }

}
