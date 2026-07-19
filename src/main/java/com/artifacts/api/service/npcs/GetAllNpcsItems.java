package com.artifacts.api.service.npcs;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.NpcsApi;
import org.openapitools.client.model.StaticDataPageNPCItemSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
@RequiredArgsConstructor
public class GetAllNpcsItems {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<StaticDataPageNPCItemSchema> retrieveAllNpcsItems(String code, String npc, String currency, Integer page, Integer size) {
        NpcsApi api = new NpcsApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StaticDataPageNPCItemSchema> response = api.getAllNpcsItemsNpcsItemsGetWithHttpInfo(null, null, null, null, size);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get All Npcs Items");
                retry.retry();
            }
        }
    }
}
