package com.artifacts.api.service.npcs;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.NpcsApi;
import org.openapitools.client.model.NPCType;
import org.openapitools.client.model.StaticDataPageNPCSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
@RequiredArgsConstructor
public class GetAllNpcs {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<StaticDataPageNPCSchema> retrieveAllNpcs(String name, NPCType npcType, String currency, String item, Integer page, Integer size) {
        NpcsApi api = new NpcsApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StaticDataPageNPCSchema> response = api.getAllNpcsNpcsDetailsGetWithHttpInfo(name, npcType, currency, item, page, size);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get All Npcs");
                retry.retry();
            }
        }
    }
}
