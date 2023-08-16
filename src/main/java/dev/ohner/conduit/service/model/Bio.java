package dev.ohner.conduit.service.model;

import jakarta.annotation.Nullable;

import java.util.Optional;

public record Bio(String value) {
    public static Optional<Bio> fromNullable(@Nullable String bio) {
        return Optional.ofNullable(bio).map(Bio::new);
    }
}
