package com.artifacts.api.caching;

import com.artifacts.api.service.npcs.GetAllNpcsItems;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.NPCItemSchema;
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
public class CacheNpcsItems {
    private final GetAllNpcsItems getAllNpcsItems;
    private final ObjectMapper objectMapper;

    private final Path NPCS_ITEMS = Paths.get("src/main/resources/data/npcs-items.json");

    public List<NPCItemSchema> fetchAllNpcsItems() throws IOException {
        List<NPCItemSchema> allData = new ArrayList<>();

        var response = getAllNpcsItems.retrieveAllNpcsItems(null, null, null, 1,10000);
        var body = response.getBody();

        if (body == null) {
            return allData;
        }

        while (body.getPage() < body.getPages()) {
            allData.addAll(body.getData());
            body.setPage(body.getPage() + 1);
            response = getAllNpcsItems.retrieveAllNpcsItems(null, null, null, body.getPage(),10000);
            body = response.getBody();
        }
        allData.addAll(body.getData());
        saveNpcsItemsToFile(allData);

        return allData;
    }

    private void saveNpcsItemsToFile(List<NPCItemSchema> npcsItems) throws IOException {
        if (Files.notExists(NPCS_ITEMS)) {
            Files.createFile(NPCS_ITEMS);
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(NPCS_ITEMS.toFile(), npcsItems);
    }

    public List<NPCItemSchema> getCachedNpcsItems() {
        try {
            return Arrays.asList(objectMapper.readValue(NPCS_ITEMS.toFile(), NPCItemSchema[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
