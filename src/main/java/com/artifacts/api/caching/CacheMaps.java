package com.artifacts.api.caching;

import com.artifacts.api.service.maps.GetAllMaps;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.MapSchema;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheMaps {
    private final GetAllMaps getAllMaps;
    private final ObjectMapper objectMapper;

    private static final String MAPS = "src/main/resources/data/maps.json";

    public List<MapSchema> fetchAllMaps() throws IOException {
        File file = new File(MAPS);

        objectMapper.writeValue(file, new ArrayList<>());
        List<MapSchema> allMapsData = new ArrayList<>();

        var response = getAllMaps.retrieveAllMaps(null, null, null, null, 1,10000);
        var allMapsBody = response.getBody();

        while (allMapsBody.getPage() < allMapsBody.getPages()) {
            allMapsData.addAll(allMapsBody.getData());
            allMapsBody.setPage(allMapsBody.getPage() + 1);
            response = getAllMaps.retrieveAllMaps(null, null, null, null, allMapsBody.getPage(),10000);
            allMapsBody = response.getBody();
        }
        allMapsData.addAll(allMapsBody.getData());
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, allMapsData);

        return allMapsData;
    }
}
