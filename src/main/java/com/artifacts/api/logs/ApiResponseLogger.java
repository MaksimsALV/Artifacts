package com.artifacts.api.logs;

import com.artifacts.tools.Beautify;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public class ApiResponseLogger {
    private static final Logger logger = LoggerFactory.getLogger(ApiResponseLogger.class);

    private final Beautify beautify;

    public ApiResponseLogger(Beautify beautify) {
        this.beautify = beautify;
    }

    public void logErrorResponse(RestClientResponseException e, String apiName) {
        try {
            logger.error("{} returned status code {}, Body: {}",
                    apiName,
                    e.getStatusCode(),
                    beautify.prettyJson(e.getResponseBodyAsString()));
        } catch (JsonProcessingException jsonException) {
            logger.error(jsonException.getMessage());
        }
    }
}
