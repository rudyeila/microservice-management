package edu.kit.microservice_management.api.controller;


import edu.kit.microservice_management.infrastructure.connectors.RepositoryCommunication;
import edu.kit.microservice_management.infrastructure.configuration.Constants;
import edu.kit.microservice_management.logic.model.metadata.GitLabMetadata;
import edu.kit.microservice_management.infrastructure.utils.json.JsonConverter;
import edu.kit.microservice_management.infrastructure.utils.logging.Logger;
import edu.kit.microservice_management.logic.operations.RegistrationOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class RegisterController implements RegisterAPI {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    JsonConverter jsonConverter;

    @Autowired
    RepositoryCommunication gitLab;

    @Autowired
    RegistrationOperations registrationOperations;

    public ResponseEntity<String> register(@RequestBody GitLabMetadata gitLabMetadata) {
        if (!checkInputIsValid(gitLabMetadata)){
            return new ResponseEntity<>(Constants.REGISTER_POST_RESPONSE_FALSE_INPUT_STRUCTURE, HttpStatus.BAD_REQUEST);
        }
        if (!gitLab.isAlive()){
            return new ResponseEntity<>(Constants.REGISTER_POST_RESPONSE_FAIL_GITLAB_OFFLINE, HttpStatus.BAD_REQUEST);
        }
        ResponseEntity responseEntity = registrationOperations.registerMicroservice(gitLabMetadata);
        return responseEntity;
    }

    private boolean checkInputIsValid(GitLabMetadata gitLabMetadata) {
        if (gitLabMetadata == null) {
            Logger.printError("No GitLabMetadata");
            return false;
        }
        if (gitLabMetadata.getDeploymentMetadata() == null) {
            Logger.printError("No Deployment Metadata");
            return false;
        }
        if (gitLabMetadata.getRepositoryMetadata() == null) {
            Logger.printError("No RepositoryMetadata");
            return false;
        }
        if (gitLabMetadata.getPipelineMetadata() == null) {
            Logger.printError("No PipelineMetadata");

            return false;
        }
        return true;
    }


}
