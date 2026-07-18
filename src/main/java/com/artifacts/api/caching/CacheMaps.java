package com.artifacts.api.caching;

import com.artifacts.api.service.maps.GetAllMaps;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.MapSchema;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheMaps {
    private final GetAllMaps getAllMaps;
    private final ObjectMapper objectMapper;

    private static final Path MAPS = Paths.get("src/main/resources/data/maps.json");

    public List<MapSchema> fetchAllMaps() throws IOException {
        List<MapSchema> allMapsData = new ArrayList<>();

        var response = getAllMaps.retrieveAllMaps(null, null, null, null, 1,10000);
        var allMapsBody = response.getBody();

        if (allMapsBody == null) {
            return allMapsData;
        }

        while (allMapsBody.getPage() < allMapsBody.getPages()) {
            allMapsData.addAll(allMapsBody.getData());
            allMapsBody.setPage(allMapsBody.getPage() + 1);
            response = getAllMaps.retrieveAllMaps(null, null, null, null, allMapsBody.getPage(),10000);
            allMapsBody = response.getBody();
        }
        allMapsData.addAll(allMapsBody.getData());
        saveMapsToFile(allMapsData);

        return allMapsData;
    }

    private void saveMapsToFile(List<MapSchema> maps) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(MAPS.toFile(), maps);
    }
}
