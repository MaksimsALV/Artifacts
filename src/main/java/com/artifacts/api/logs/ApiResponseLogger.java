package com.artifacts.api.logs;

import com.artifacts.tools.Beautify;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public class ApiResponseLogger {
    private final Beautify beautify;

    public ApiResponseLogger(Beautify beautify) {
        this.beautify = beautify;
    }

    public void logErrorResponse(Logger logger, RestClientResponseException e) {
        try {
            logger.error("returned status code {}, Body: {}",
                    e.getStatusCode(),
                    beautify.prettyJson(e.getResponseBodyAsString()));
        } catch (JsonProcessingException jsonException) {
            logger.error(jsonException.getMessage());
        }
    }
}
