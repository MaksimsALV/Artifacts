package com.artifacts.api.service.maps;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GetAllMaps {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(GetAllMaps.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<StaticDataPageMapSchema> retrieveAllMaps(MapLayer layer, MapContentType contentType, String contentCode, Boolean hideBlockedMaps, Boolean hideEvent, Boolean transition, Integer page, Integer size) { //todo need to check if <T> is correct one here, maybe should be StaticDataPageMapSchema or smth
        MapsApi mapsApi = new MapsApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StaticDataPageMapSchema> response = mapsApi.getAllMapsMapsGetWithHttpInfo(layer, contentType, contentCode, hideBlockedMaps, hideEvent, transition, page, size);

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
