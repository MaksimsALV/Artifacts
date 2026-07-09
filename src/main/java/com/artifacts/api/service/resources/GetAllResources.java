package com.artifacts.api.service.resources;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MapsApi;
import org.openapitools.client.api.ResourcesApi;
import org.openapitools.client.model.GatheringSkill;
import org.openapitools.client.model.ResourceResponseSchema;
import org.openapitools.client.model.StaticDataPageMapSchema;
import org.openapitools.client.model.StaticDataPageResourceSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
public class GetAllResources {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public GetAllResources(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public ResponseEntity<StaticDataPageResourceSchema> retrieveAllResources(GatheringSkill skill) {
        ResourcesApi resourcesApi = new ResourcesApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StaticDataPageResourceSchema> response = resourcesApi.getAllResourcesResourcesGetWithHttpInfo(null, null, skill, null, null, 100);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get All Resources");
                retry.retry();
            }
        }
    }

//    public List<String> retrieveAllResourceCodesAsList(GatheringSkill skill) {
//        var allResources = retrieveAllResources(skill) {
//
//        }
//    }
}
