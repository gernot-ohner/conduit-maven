package dev.ohner.conduit.repository.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Table("user_follower_relation")
public record UserFollowerRelationEntity(
    @Id
    UUID id,
    UUID userId,
    UUID followerId,
    @CreatedDate
    OffsetDateTime createdAt
) {
}
