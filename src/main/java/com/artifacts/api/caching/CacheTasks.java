package com.artifacts.api.caching;

import com.artifacts.api.tasks.GetAllTasks;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.TaskFullSchema;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheTasks {
    private final GetAllTasks getAllTasks;
    private final ObjectMapper objectMapper;

    private final Path TASKS = Paths.get("src/main/resources/data/tasks.json");

    public List<TaskFullSchema> fetchAllTasks() throws IOException {
        List<TaskFullSchema> allData = new ArrayList<>();

        var response = getAllTasks.retrieveAllTasks(null, null, null, null, 1,10000);
        var body = response.getBody();

        if (body == null) {
            return allData;
        }

        while (body.getPage() < body.getPages()) {
            allData.addAll(body.getData());
            body.setPage(body.getPage() + 1);
            response = getAllTasks.retrieveAllTasks(null, null, null, null, body.getPage(),10000);
            body = response.getBody();
        }
        allData.addAll(body.getData());
        saveTasksToFile(allData);

        return allData;
    }

    private void saveTasksToFile(List<TaskFullSchema> tasks) throws IOException {
        if (Files.notExists(TASKS)) {
            Files.createFile(TASKS);
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(TASKS.toFile(), tasks);
    }

    public List<TaskFullSchema> getCachedTasks() {
        try {
            return Arrays.asList(objectMapper.readValue(TASKS.toFile(), TaskFullSchema[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
