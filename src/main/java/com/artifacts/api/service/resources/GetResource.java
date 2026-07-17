package com.artifacts.api.service.resources;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.ResourcesApi;
import org.openapitools.client.model.ResourceResponseSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
@RequiredArgsConstructor
public class GetResource {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<ResourceResponseSchema> retrieveResource(String resource) {
        ResourcesApi resourcesApi = new ResourcesApi(apiClient);

        while (true) {
            try {
                ResponseEntity<ResourceResponseSchema> response = resourcesApi.getResourceResourcesCodeGetWithHttpInfo(resource);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get Resource");
                retry.retry();
            }
        }
    }
}
