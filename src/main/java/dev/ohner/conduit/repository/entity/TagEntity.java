package dev.ohner.conduit.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("tags")
public record TagEntity(
    @Id
    UUID id,
    String tag
) {
}
