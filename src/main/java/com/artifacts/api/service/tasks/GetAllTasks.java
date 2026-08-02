package com.artifacts.api.service.tasks;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.TasksApi;
import org.openapitools.client.model.Skill;
import org.openapitools.client.model.StaticDataPageTaskFullSchema;
import org.openapitools.client.model.TaskType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
@RequiredArgsConstructor
public class GetAllTasks {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<StaticDataPageTaskFullSchema> retrieveAllTasks(Integer minLevel, Integer maxLevel, Skill skill, TaskType taskType, Integer page, Integer size) {
        TasksApi api = new TasksApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StaticDataPageTaskFullSchema> response = api.getAllTasksTasksListGetWithHttpInfo(minLevel, maxLevel, skill, taskType, page, size);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get All Tasks");
                retry.retry();
            }
        }
    }
}
