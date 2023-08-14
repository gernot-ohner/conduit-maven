package dev.ohner.conduit.repository.entity;

import dev.ohner.conduit.service.model.Slug;
import jakarta.validation.Valid;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.List;

public record ArticleEntity(
    @Id
    Slug slug,
    String title,
    String description,
    String body,
    @Valid
    List<String> tagList,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime createdAt,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime updatedAt,
    Boolean favorited,
    Integer favoritesCount,
    @Reference
    UserEntity author
) {
}
