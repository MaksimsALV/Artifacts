package com.artifacts.api.caching;

import com.artifacts.api.service.maps.GetAllMaps;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;


@RequiredArgsConstructor
class CacheMapsTest {
    private ApiClient apiClient;

    @BeforeEach
    void setUp() {
        apiClient = new ApiClient();
        apiClient.setBasePath("https://api.artifactsmmo.com");
    }

    @Test
    void fetchAllMaps() throws IOException {
        var getAllMaps = new GetAllMaps(apiClient, null, null);
        var objectMapper = new ObjectMapper();
        var cacheMaps = new CacheMaps(getAllMaps, objectMapper);

        var allMaps = cacheMaps.fetchAllMaps();
        assertFalse(allMaps.isEmpty());
    }
}