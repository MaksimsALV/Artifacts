package com.artifacts.api.service.items;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.ItemsApi;
import org.openapitools.client.model.CraftSkill;
import org.openapitools.client.model.ItemType;
import org.openapitools.client.model.StaticDataPageItemSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
@RequiredArgsConstructor
public class GetAllItems {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<StaticDataPageItemSchema> retrieveAllItems(String name, Integer minLevel, Integer maxLevel, ItemType itemType, CraftSkill craftSkill, String craftMaterial, Integer page, Integer size) {
        ItemsApi api = new ItemsApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StaticDataPageItemSchema> response = api.getAllItemsItemsGetWithHttpInfo(name, minLevel, maxLevel, itemType, craftSkill, craftMaterial, page, size);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get All Items");
                retry.retry();
            }
        }
    }
}
