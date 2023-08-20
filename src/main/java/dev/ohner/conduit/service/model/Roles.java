package dev.ohner.conduit.service.model;

import java.util.Arrays;
import java.util.List;

public record Roles(String value) {
    public List<String> getRoles() {
        return Arrays.asList(value.split(","));
    }
}
