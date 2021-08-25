package edu.kit.microservice_management.api.controller;

import edu.kit.microservice_management.logic.model.metadata.GitLabMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/unregister")
public interface UnregisterAPI {

    @PostMapping
    ResponseEntity<String> unregister(@RequestBody GitLabMetadata gitLabMetadata);

}
