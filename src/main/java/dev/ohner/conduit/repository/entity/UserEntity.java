package dev.ohner.conduit.repository.entity;

import dev.ohner.conduit.service.model.*;
import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Optional;
import java.util.UUID;

@Table("users")
public record UserEntity(
    @Id
    UUID id,
    @Email
    String email,
    String token,
    String username,
    Optional<String> bio,
    Optional<String> image
) {
}
