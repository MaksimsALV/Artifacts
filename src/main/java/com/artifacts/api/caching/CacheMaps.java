package com.artifacts.api.caching;

import com.artifacts.api.service.maps.GetAllMaps;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.MapSchema;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheMaps {
    private final GetAllMaps getAllMaps;

    public List getAllMaps() {
        var allMapsBody = getAllMaps.retrieveAllMaps(null, null, null, null, 1,10000).getBody();
        if (allMapsBody.getPage() != allMapsBody.getPages()) {

        }
        return allMapsData;
    }
}
