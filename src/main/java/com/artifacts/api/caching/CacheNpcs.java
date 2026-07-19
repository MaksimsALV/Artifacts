package com.artifacts.api.caching;

import com.artifacts.api.service.npcs.GetAllNpcs;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.NPCSchema;
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
public class CacheNpcs {
    private final GetAllNpcs getAllNpcs;
    private final ObjectMapper objectMapper;

    private final Path NPCS = Paths.get("src/main/resources/data/npcs.json");

    public List<NPCSchema> fetchAllNpcs() throws IOException {
        List<NPCSchema> allData = new ArrayList<>();

        var response = getAllNpcs.retrieveAllNpcs(null, null, null, null, 1,10000);
        var body = response.getBody();

        if (body == null) {
            return allData;
        }

        while (body.getPage() < body.getPages()) {
            allData.addAll(body.getData());
            body.setPage(body.getPage() + 1);
            response = getAllNpcs.retrieveAllNpcs(null, null, null, null, body.getPage(),10000);
            body = response.getBody();
        }
        allData.addAll(body.getData());
        saveNpcsToFile(allData);

        return allData;
    }

    private void saveNpcsToFile(List<NPCSchema> npcs) throws IOException {
        if (Files.notExists(NPCS)) {
            Files.createFile(NPCS);
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(NPCS.toFile(), npcs);
    }

    public List<NPCSchema> getCachedNpcs() {
        try {
            return Arrays.asList(objectMapper.readValue(NPCS.toFile(), NPCSchema[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
