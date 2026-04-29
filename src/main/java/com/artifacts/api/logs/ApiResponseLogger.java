package com.artifacts.api.logs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public class ApiResponseLogger {
    private final ObjectMapper objectMapper;

    public ApiResponseLogger(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void logErrorResponse(Logger logger, RestClientResponseException e) {
        try {
            logger.error("returned status code {}, Body: {}",
                    e.getStatusCode(),
                    prettyJson(e.getResponseBodyAsString()));
        } catch (JsonProcessingException jsonException) {
            logger.error(jsonException.getMessage());
        }
    }

    //todo need to move this to tools
    public String prettyJson(Object value) throws JsonProcessingException {
        return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(value);
    }
}
