package com.artifacts.api.caching;

import com.artifacts.api.service.account.GetBankItems;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.CharacterSchema;
import org.openapitools.client.model.SimpleItemSchema;
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
public class CacheBankItems {
    private final GetBankItems getBankItems;
    private final ObjectMapper objectMapper;

    private final Path BANK_ITEMS = Paths.get("src/main/resources/data/bank-items.json");

    public List<SimpleItemSchema> fetchAllBankItems() throws IOException {
        List<SimpleItemSchema> allData = new ArrayList<>();

        var response = getBankItems.retrieveBankItems(null, 1,100);
        var body = response.getBody();

        if (body == null) {
            return allData;
        }

        while (body.getPage() < body.getPages()) {
            allData.addAll(body.getData());
            body.setPage(body.getPage() + 1);
            response = getBankItems.retrieveBankItems(null, body.getPage(),100);
            body = response.getBody();
        }
        allData.addAll(body.getData());
        saveBankItemsToFile(allData);

        return allData;
    }

    public void updateBank(List<SimpleItemSchema> items) {
        try {
            saveBankItemsToFile(items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveBankItemsToFile(List<SimpleItemSchema> items) throws IOException {
        if (Files.notExists(BANK_ITEMS)) {
            Files.createFile(BANK_ITEMS);
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(BANK_ITEMS.toFile(), items);
    }

    public List<SimpleItemSchema> getCachedBankItems() {
        try {
            return Arrays.asList(objectMapper.readValue(BANK_ITEMS.toFile(), SimpleItemSchema[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
