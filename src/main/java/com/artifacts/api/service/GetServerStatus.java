package com.artifacts.api.service;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Beautify;
import com.artifacts.tools.Retry;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.ServerDetailsApi;
import org.openapitools.client.model.StatusResponseSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;

@Service
@RequiredArgsConstructor
public class GetServerStatus {
    private final ApiClient apiClient;
    private final Logger logger =  LoggerFactory.getLogger(GetServerStatus.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;
    private final Beautify beautify;

    public ResponseEntity<StatusResponseSchema> getServerStatus() {
        ServerDetailsApi serverDetailsApi = new ServerDetailsApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StatusResponseSchema> response = serverDetailsApi.getServerDetailsGetWithHttpInfo();
//                System.out.println(beautify.prettyJson(response.getBody()));

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

//            } catch (JsonProcessingException e) {
//                logger.error("Failed to serialize response", e);

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get Server Status");
                retry.retry();
            }
        }
    }

    public boolean serverIsUp() {
        return getServerStatus().getStatusCode().value() == SUCCESS;
    }
}
