package com.artifacts.api.caching;

import com.artifacts.api.service.items.GetAllItems;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.ItemSchema;
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
public class CacheItems {
    private final GetAllItems getAllItems;
    private final ObjectMapper objectMapper;

    private final Path ITEMS = Paths.get("src/main/resources/data/items.json");

    public List<ItemSchema> fetchAllItems() throws IOException {
        List<ItemSchema> allData = new ArrayList<>();

        var response = getAllItems.retrieveAllItems(null, null, null, null, null, null, 1,10000);
        var body = response.getBody();

        if (body == null) {
            return allData;
        }

        while (body.getPage() < body.getPages()) {
            allData.addAll(body.getData());
            body.setPage(body.getPage() + 1);
            response = getAllItems.retrieveAllItems(null, null, null, null, null, null, body.getPage(),10000);
            body = response.getBody();
        }
        allData.addAll(body.getData());
        saveItemsToFile(allData);

        return allData;
    }

    private void saveItemsToFile(List<ItemSchema> items) throws IOException {
        if (Files.notExists(ITEMS)) {
            Files.createFile(ITEMS);
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(ITEMS.toFile(), items);
    }

    public List<ItemSchema> getCachedItems() {
        try {
            return Arrays.asList(objectMapper.readValue(ITEMS.toFile(), ItemSchema[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
