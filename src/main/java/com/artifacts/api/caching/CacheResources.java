package com.artifacts.api.caching;

import com.artifacts.api.service.resources.GetAllResources;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.ResourceSchema;
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
public class CacheResources {
    private final GetAllResources getAllResources;
    private final ObjectMapper objectMapper;

    private final Path RESOURCES = Paths.get("src/main/resources/data/resources.json");

    public List<ResourceSchema> fetchAllResources() throws IOException {
        List<ResourceSchema> allData = new ArrayList<>();

        var response = getAllResources.retrieveAllResources(null, null, null, null, 1,10000);
        var body = response.getBody();

        if (body == null) {
            return allData;
        }

        while (body.getPage() < body.getPages()) {
            allData.addAll(body.getData());
            body.setPage(body.getPage() + 1);
            response = getAllResources.retrieveAllResources(null, null, null, null, body.getPage(),10000);
            body = response.getBody();
        }
        allData.addAll(body.getData());
        saveResourcesToFile(allData);

        return allData;
    }

    private void saveResourcesToFile(List<ResourceSchema> resources) throws IOException {
        if (Files.notExists(RESOURCES)) {
            Files.createFile(RESOURCES);
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(RESOURCES.toFile(), resources);
    }

    public List<ResourceSchema> getCachedResources() {
        try {
            return Arrays.asList(objectMapper.readValue(RESOURCES.toFile(), ResourceSchema[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
