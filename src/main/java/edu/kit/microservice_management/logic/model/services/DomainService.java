package edu.kit.microservice_management.logic.model.services;

import edu.kit.microservice_management.infrastructure.connectors.DomainServiceCommunication;
import edu.kit.microservice_management.infrastructure.configuration.Constants;
import edu.kit.microservice_management.infrastructure.utils.json.JsonConverter;
import edu.kit.microservice_management.infrastructure.utils.logging.Logger;
import edu.kit.microservice_management.logic.model.Microservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DomainService {
    DomainServiceCommunication domainServiceCommunication;
    JsonConverter jsonConverter;

    @Autowired
    public void setDomainServiceCommunication(DomainServiceCommunication domainServiceCommunication) {
        this.domainServiceCommunication = domainServiceCommunication;
    }

    @Autowired
    public void setJsonConverter(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    public Collection<Microservice>  getMicroservices() {
        return domainServiceCommunication.getMicroservices();
    }

    public Microservice getSingleMicroservice(String deployment_name) {
        return domainServiceCommunication.getSingleMicroservice(deployment_name);
    }


    public ResponseEntity sendToDomain(Microservice microservice) {
        boolean isAlreadyInDatabase = domainServiceCommunication.checkMicroserviceInDatabase(microservice);
        String microserviceJson = jsonConverter.objectToJson(microservice);
        System.out.println(microserviceJson);

        if (isAlreadyInDatabase) {
            Logger.printMessage("Microservice already in Database -> Update");
            if (domainServiceCommunication.sendMicroservice(microserviceJson)){
                return new ResponseEntity<>(Constants.REGISTER_POST_RESPONSE_SUCCESS, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Constants.REGISTER_POST_RESPONSE_ERROR_WHILE_SENDING_MICROSERVICE_TO_SERVICE, HttpStatus.BAD_REQUEST);
            }
        } else {
            Logger.printMessage("Microservice not in Database -> Create and Trigger");
            domainServiceCommunication.sendMicroservice(microserviceJson);
            return new ResponseEntity<>(Constants.REGISTER_POST_RESPONSE_SUCCESS, HttpStatus.OK);
        }
    }

    public boolean isAlive() {
        boolean isAlive = domainServiceCommunication.makeEmptyGet();
        if (isAlive) {
            Logger.printMessage("Domain Service is online!");
        } else {
            Logger.printError("Domain Service is offline");
        }
        return isAlive;
    }

    public void deleteMicroserviceByHostName(String hostName) {
        Logger.printMessage("Microservice " + hostName + " will be deleted from Domain Service");
        domainServiceCommunication.deleteMicroserviceByHostName(hostName);
        Logger.printMessage("Microservice is deleted from Domain Service");
    }
}
