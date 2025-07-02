package com.qentelli.employeetrackingsystem.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.qentelli.employeetrackingsystem.entity.TechStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlexibleEnumListDeserializer extends JsonDeserializer<List<TechStack>> {

    @Override
    public List<TechStack> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        List<TechStack> result = new ArrayList<>();

        if (node.isArray()) {
            for (JsonNode item : node) {
                result.add(normalizeEnum(item.asText()));
            }
        } else if (node.isTextual()) {
            result.add(normalizeEnum(node.asText()));
        } else {
            throw new IllegalArgumentException("techStack must be a string or an array of strings");
        }

        return result;
    }

    private TechStack normalizeEnum(String rawValue) {
        String normalized = rawValue.trim().toUpperCase(); // Supports values like "Frontend"
        try {
            return TechStack.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                "Invalid techStack value: '" + rawValue + "'. Allowed values are: " + Arrays.toString(TechStack.values())
            );
        }
    }
}