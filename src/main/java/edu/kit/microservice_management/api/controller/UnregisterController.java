package edu.kit.microservice_management.api.controller;


import edu.kit.microservice_management.logic.model.metadata.GitLabMetadata;
import edu.kit.microservice_management.logic.operations.RegistrationOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UnregisterController implements UnregisterAPI {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    RegistrationOperations registrationOperations;

    @PostMapping
    public ResponseEntity<String> unregister(@RequestBody GitLabMetadata gitLabMetadata) {
        ResponseEntity responseEntity = registrationOperations.unregisterMicroservice(gitLabMetadata);
        return responseEntity;
    }


}
