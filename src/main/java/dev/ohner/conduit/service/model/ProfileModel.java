package dev.ohner.conduit.service.model;

import dev.ohner.conduit.model.Profile;
import dev.ohner.conduit.repository.entity.UserEntity;

import java.util.Optional;

public record ProfileModel(
    Username username,
    Optional<Bio> bio,
    Optional<ImageReference> image,
    boolean following
) {
    public static ProfileModel fromUserEntity(UserEntity user, boolean b) {
        return new ProfileModel(
            new Username(user.username()),
            Bio.fromNullable(user.bio()),
            ImageReference.fromNullable(user.image()),
            b
        );
    }

    public Profile toProfile() {
        return new Profile(
            username.value(),
            bio.map(Bio::value).orElse(null),
            image.map(ImageReference::value).orElse(null),
            following
        );
    }
}
