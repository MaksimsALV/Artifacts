package com.artifacts.api.service.monsters;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MonstersApi;
import org.openapitools.client.model.StaticDataPageMonsterSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
@RequiredArgsConstructor
public class GetAllMonsters {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<StaticDataPageMonsterSchema> retrieveAllMonsters(String name, Integer minLevel, Integer maxLevel, String drop, Integer page, Integer size) {
        MonstersApi api = new MonstersApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StaticDataPageMonsterSchema> response = api.getAllMonstersMonstersGetWithHttpInfo(name, minLevel, maxLevel, drop, page, size);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get All Monsters");
                retry.retry();
            }
        }
    }
}
