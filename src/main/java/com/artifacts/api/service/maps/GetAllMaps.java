package com.artifacts.api.service.maps;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MapsApi;
import org.openapitools.client.model.MapContentType;
import org.openapitools.client.model.MapLayer;
import org.openapitools.client.model.StaticDataPageMapSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.SUCCESS;

//todo tests
@Service
public class GetAllMaps {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(GetAllMaps.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public GetAllMaps(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public ResponseEntity<StaticDataPageMapSchema> retrieveAllMaps(MapLayer layer, MapContentType contentType, String contentCode, Boolean transition) { //todo need to check if <T> is correct one here, maybe should be StaticDataPageMapSchema or smth
        MapsApi mapsApi = new MapsApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StaticDataPageMapSchema> response = mapsApi.getAllMapsMapsGetWithHttpInfo(layer, contentType, contentCode, transition, null, null, null, 10000);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get All Maps");
                retry.retry();
            }
        }
    }
}
