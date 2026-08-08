package com.artifacts.api.caching;

import com.artifacts.api.service.mycharacters.GetMyCharacters;
import com.artifacts.game.account.MyCharacters;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.CharacterSchema;
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
public class CacheMyCharacters {
    private final GetMyCharacters getMyCharacters;
    private final ObjectMapper objectMapper;

    private final Path CHARACTERS = Paths.get("src/main/resources/data/characters.json");

    public List<CharacterSchema> fetchAllCharacters() throws IOException {
        List<CharacterSchema> allData = new ArrayList<>();

        var response = getMyCharacters.retrieveMyCharacters();
        var body = response.getBody();

        if (body == null) {
            return allData;
        }

        allData.addAll(body.getData());
        saveCharactersToFile(allData);

        return allData;
    }

    public void updateCharacter(CharacterSchema character) {
        try {
            var characters = getCachedCharacters().stream()
                    .map(c -> c.getName().equals(character.getName())
                            ? character
                            : c)
                    .toList();

            saveCharactersToFile(characters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveCharactersToFile(List<CharacterSchema> characters) throws IOException {
        if (Files.notExists(CHARACTERS)) {
            Files.createFile(CHARACTERS);
        }
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(CHARACTERS.toFile(), characters);
    }

    public List<CharacterSchema> getCachedCharacters() {
        try {
            return Arrays.asList(objectMapper.readValue(CHARACTERS.toFile(), CharacterSchema[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CharacterSchema getCachedCharacter(MyCharacters character) {
        return getCachedCharacters().stream()
                .filter(c -> c.getName().equals(character.getName()))
                .findFirst()
                .orElseThrow();
    }
}
