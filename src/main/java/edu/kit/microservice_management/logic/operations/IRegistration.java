package edu.kit.microservice_management.logic.operations;

import edu.kit.microservice_management.logic.model.metadata.GitLabMetadata;
import org.springframework.http.ResponseEntity;

public interface IRegistration {

    ResponseEntity registerMicroservice(GitLabMetadata gitLabMetadata);
    ResponseEntity unregisterMicroservice(GitLabMetadata gitLabMetadata);

}
