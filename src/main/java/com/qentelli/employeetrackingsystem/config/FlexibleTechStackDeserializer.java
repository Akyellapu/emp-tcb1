package com.qentelli.employeetrackingsystem.config;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.qentelli.employeetrackingsystem.entity.TechStack;

import java.io.IOException;

public class FlexibleTechStackDeserializer extends JsonDeserializer<TechStack> {

    @Override
    public TechStack deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String raw = p.getText().trim().toUpperCase();
        try {
            return TechStack.valueOf(raw);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid techStack: '" + raw + "'. Allowed values: " +
                java.util.Arrays.toString(TechStack.values()));
        }
    }
}