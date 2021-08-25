package edu.kit.microservice_management.logic.operations;

import edu.kit.microservice_management.infrastructure.configuration.Constants;
import edu.kit.microservice_management.infrastructure.utils.logging.Logger;
import edu.kit.microservice_management.logic.model.services.DomainService;
import edu.kit.microservice_management.logic.model.Microservice;
import edu.kit.microservice_management.logic.model.MicroserviceBuilder;
import edu.kit.microservice_management.logic.model.metadata.GitLabMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationOperations implements IRegistration {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public ResponseEntity registerMicroservice(GitLabMetadata gitLabMetadata) {
        Microservice microservice = fetchRepository(gitLabMetadata);
        ResponseEntity responseEntity = createMicroservice(microservice);
        return responseEntity;
    }

    @Override
    public ResponseEntity unregisterMicroservice(GitLabMetadata gitLabMetadata) {
        String hostName = getHostName(gitLabMetadata);
        Logger.printMessage("Unregister the Microservice: " + hostName);
        ResponseEntity responseEntity = deleteMicroservice(hostName);
        return responseEntity;
    }


    private Microservice fetchRepository(GitLabMetadata gitLabMetadata) {
        MicroserviceBuilder microserviceBuilder = applicationContext.getBean(MicroserviceBuilder.class);
        Microservice microservice = microserviceBuilder.createMicroservice(gitLabMetadata.getPipelineMetadata(),
                gitLabMetadata.getRepositoryMetadata(), gitLabMetadata.getDeploymentMetadata());
        return microservice;
    }

    private ResponseEntity createMicroservice(Microservice microservice) {
        DomainService domainService = applicationContext.getBean(DomainService.class);
        if (domainService.isAlive()) {
            return domainService.sendToDomain(microservice);
        } else {
            return new ResponseEntity<>(Constants.REGISTER_POST_RESPONSE_FAIL_SERVICE_OFFLINE, HttpStatus.BAD_REQUEST);
        }
    }


    private String getHostName(GitLabMetadata gitLabMetadata) {
        return gitLabMetadata.getDeploymentMetadata().getRelease();
    }

    private ResponseEntity deleteMicroservice(String hostName) {
        DomainService domainService = applicationContext.getBean(DomainService.class);
        if (domainService.isAlive()) {
            domainService.deleteMicroserviceByHostName(hostName);
            return new ResponseEntity<>(Constants.UNREGISTER_POST_RESPONSE_SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Constants.UNREGISTER_POST_RESPONSE_FAIL, HttpStatus.BAD_REQUEST);
        }
    }
}
