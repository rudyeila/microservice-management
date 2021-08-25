package edu.kit.microservice_management.api.controller;


import edu.kit.microservice_management.infrastructure.configuration.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class DefaultController {

    @GetMapping
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>(Constants.DEFAULT_HTTP_GET_ANSWER, HttpStatus.OK);
    }
}
