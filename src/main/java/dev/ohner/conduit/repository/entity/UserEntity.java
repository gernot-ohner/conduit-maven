package dev.ohner.conduit.repository.entity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("users")
public record UserEntity(
    @Id
    UUID id,
    @Email
    String email,
    String token,
    String username,
    @Nullable
    String bio,
    @Nullable
    String image
) {
}
