package com.artifacts.game.resources.gathering;

import com.artifacts.api.caching.CacheMaps;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.DestinationSchema;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final CacheMaps cacheMaps;

    public DestinationSchema destination(String contentCode) {
        var location = cacheMaps.getCachedMaps().stream()
                .filter(map -> map.getInteractions() != null)
                .filter(map -> map.getInteractions().getContent() != null)
                .filter(map -> map.getInteractions().getContent().getCode().equals(contentCode))
                .findFirst()
                .orElseThrow();
        return new DestinationSchema()
                .x(location.getX())
                .y(location.getY())
                .mapId(location.getMapId());
    }

    public DestinationSchema entranceToGoldMineLocation() {
        return new DestinationSchema()
                .x(5)
                .y(-3)
                .mapId(134);
    }

    public DestinationSchema entranceToMithrilMineLocation() {
        return new DestinationSchema()
                .x(-2)
                .y(6)
                .mapId(571);
    }
}
