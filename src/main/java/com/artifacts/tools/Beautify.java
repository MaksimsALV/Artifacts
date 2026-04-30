package com.artifacts.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class Beautify {
    private final ObjectMapper objectMapper;

    public Beautify(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public String prettyJson(Object value) throws JsonProcessingException {
        return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(value);
    }
}
