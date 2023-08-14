package dev.ohner.conduit.service.model;

import dev.ohner.conduit.repository.entity.TagEntity;

import java.util.UUID;

public record TagModel(
    UUID id,
    String tag
) {

    public static TagModel fromEntity(TagEntity entity) {
        return new TagModel(entity.id(), entity.tag());
    }

    public static TagModel from(String tag) {
        return new TagModel(UUID.randomUUID(), tag);
    }
}
