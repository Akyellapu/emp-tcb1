package com.qentelli.employeetrackingsystem.config;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.qentelli.employeetrackingsystem.entity.TechStack;

import java.io.IOException;
public class FlexibleEnumListDeserializer extends JsonDeserializer<List<TechStack>> {
    @Override
    public List<TechStack> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        List<TechStack> result = new ArrayList<>();

        if (node.isArray()) {
            for (JsonNode item : node) {
                result.add(TechStack.valueOf(item.asText().toUpperCase()));
            }
        } else if (node.isTextual()) {
            result.add(TechStack.valueOf(node.asText().toUpperCase()));
        }

        return result;
    }
}