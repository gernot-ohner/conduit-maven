package dev.ohner.conduit.service.model;


import jakarta.annotation.Nullable;

import java.util.Optional;

public record ImageReference(String value) {

    public static Optional<ImageReference> ofNullable(@Nullable String image) {
        return Optional.ofNullable(image).map(ImageReference::new);
    }
}
