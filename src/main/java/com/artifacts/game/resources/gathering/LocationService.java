package com.artifacts.game.resources.gathering;

import com.artifacts.api.service.maps.GetAllMaps;
import org.openapitools.client.model.DestinationSchema;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private final GetAllMaps getAllMaps;
    public LocationService(GetAllMaps getAllMaps) {
        this.getAllMaps = getAllMaps;
    }

    public DestinationSchema destination(String contentCode) {
        var mapData = getAllMaps.retrieveAllMaps(null, null, contentCode, null);
        var location = mapData.getBody().getData().getFirst();
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
