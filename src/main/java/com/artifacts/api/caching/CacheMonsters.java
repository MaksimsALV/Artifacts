package com.artifacts.api.caching;

import com.artifacts.api.service.monsters.GetAllMonsters;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.MonsterSchema;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheMonsters {
    private final GetAllMonsters getAllMonsters;
    private final ObjectMapper objectMapper;

    private static final Path MONSTERS = Paths.get("src/main/resources/data/monsters.json");

    public List<MonsterSchema> fetchAllMonsters() throws IOException {
        List<MonsterSchema> allData = new ArrayList<>();

        var response = getAllMonsters.retrieveAllMonsters(null, null, null, null, 1,10000);
        var body = response.getBody();

        if (body == null) {
            return allData;
        }

        while (body.getPage() < body.getPages()) {
            allData.addAll(body.getData());
            body.setPage(body.getPage() + 1);
            response = getAllMonsters.retrieveAllMonsters(null, null, null, null, body.getPage(),10000);
            body = response.getBody();
        }
        allData.addAll(body.getData());
        saveMonstersToFile(allData);

        return allData;
    }

    private void saveMonstersToFile(List<MonsterSchema> monsters) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(MONSTERS.toFile(), monsters);
    }

    public List<MonsterSchema> getCachedMonsters() {
        try {
            return Arrays.asList(objectMapper.readValue(MONSTERS.toFile(), MonsterSchema[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
