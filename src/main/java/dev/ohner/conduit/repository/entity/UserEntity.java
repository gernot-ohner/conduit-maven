package dev.ohner.conduit.repository.entity;

import dev.ohner.conduit.service.model.*;
import org.springframework.data.annotation.Id;

import java.util.Optional;

public record UserEntity(
    @Id
    EmailRecord emailRecord,
    Token token,
    Username username,
    Optional<Bio> bio,
    Optional<ImageReference> image
) {
}
