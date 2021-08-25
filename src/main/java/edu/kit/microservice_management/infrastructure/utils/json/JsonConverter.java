package edu.kit.microservice_management.infrastructure.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.microservice_management.infrastructure.configuration.Constants;
import edu.kit.microservice_management.infrastructure.utils.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonConverter {
    @Autowired
    private ObjectMapper objectMapper;

    public String objectToJson(Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Logger.printError("Cant convert Object to json Array");
            return Constants.EMPTY;
        }
    }
}
