package dev.ohner.conduit.service.model;

import dev.ohner.conduit.model.User;
import dev.ohner.conduit.repository.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public record UserModel(
    UUID id,
    EmailRecord emailRecord,
    Token token,
    Username username,
    Optional<Bio> bio,
    Optional<ImageReference> image
) {

    public static UserModel fromEntity(UserEntity entity) {
        return new UserModel(
            entity.id(),
            new EmailRecord(entity.email()),
            new Token(entity.token()),
            new Username(entity.username()),
            entity.bio().map(Bio::new),
            entity.image().map(ImageReference::new)
        );
    }

    public UserEntity toEntity() {
        return new UserEntity(
            id(),
            emailRecord().value(),
            token().value(),
            username().value(),
            bio().map(Bio::value),
            image().map(ImageReference::value)
        );
    }

    public User toUser() {
        return new User(
            emailRecord().value(),
            token().value(),
            username().value(),
            bio().map(Bio::value).orElse(null),
            image().map(ImageReference::value).orElse(null)
        );
    }
}
