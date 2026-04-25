package com.artifacts.api.logs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseLogger {
    private final ObjectMapper objectMapper;

    public ApiResponseLogger(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void logErrorResponse(Logger logger, ResponseEntity<?> response) {
        try {
            logger.error("returned status code {}, Body: {}",
                    response.getStatusCode(),
                    prettyJson(response.getBody()));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    public String prettyJson(Object value) throws JsonProcessingException {
        return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(value);
    }
}
