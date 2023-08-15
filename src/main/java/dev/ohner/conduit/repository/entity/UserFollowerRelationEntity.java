package dev.ohner.conduit.repository.entity;

import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("user_follower_relation")
public record UserFollowerRelationEntity(
    UUID id,
    UUID userId,
    UUID followerId
) {
}
