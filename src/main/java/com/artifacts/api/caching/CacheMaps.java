package com.artifacts.api.caching;

import com.artifacts.api.service.maps.GetAllMaps;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.MapSchema;
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
public class CacheMaps {
    private final GetAllMaps getAllMaps;
    private final ObjectMapper objectMapper;

    private final Path MAPS = Paths.get("src/main/resources/data/maps.json");

    public List<MapSchema> fetchAllMaps() throws IOException {
        List<MapSchema> allData = new ArrayList<>();

        var response = getAllMaps.retrieveAllMaps(null, null, null, null, 1,10000);
        var body = response.getBody();

        if (body == null) {
            return allData;
        }

        while (body.getPage() < body.getPages()) {
            allData.addAll(body.getData());
            body.setPage(body.getPage() + 1);
            response = getAllMaps.retrieveAllMaps(null, null, null, null, body.getPage(),10000);
            body = response.getBody();
        }
        allData.addAll(body.getData());
        saveMapsToFile(allData);

        return allData;
    }

    private void saveMapsToFile(List<MapSchema> maps) throws IOException {
        if (Files.notExists(MAPS)) {
            Files.createFile(MAPS);
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(MAPS.toFile(), maps);
    }

    public List<MapSchema> getCachedMaps() {
        try {
            return Arrays.asList(objectMapper.readValue(MAPS.toFile(), MapSchema[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
