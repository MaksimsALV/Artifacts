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

    public DestinationSchema resourceLocation(String resourceCode) {
        var maps = getAllMaps.retrieveAllMaps(null, null, resourceCode, null);
        //todo stream getData instead of findFirst and do toList for better later usage in case there is more than one location
        var locationData = maps.getBody().getData().getFirst();
        return new DestinationSchema()
                .x(locationData.getX())
                .y(locationData.getY())
                .mapId(locationData.getMapId());
    }

    public DestinationSchema goldMineLocation() {
        return new DestinationSchema()
                .x(5)
                .y(-3)
                .mapId(134);
    }

    public DestinationSchema mithrilMineLocation() {
        return new DestinationSchema()
                .x(-2)
                .y(6)
                .mapId(571);
    }
}
