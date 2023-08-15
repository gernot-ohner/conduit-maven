package dev.ohner.conduit.service.model;

import dev.ohner.conduit.model.UpdateUser;
import dev.ohner.conduit.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserModel> getUserById(UUID id) {
        return userRepository.findById(id)
            .map(UserModel::fromEntity);
    }

    public Optional<UserModel> getUserByEmail(EmailRecord email) {
        return userRepository.findByEmail(email.value())
            .map(UserModel::fromEntity);
    }

    public Optional<UserModel> updateUserByEmail(EmailRecord emailRecord, @NotNull @Valid UpdateUser user) {
        final var maybeCurrentUser = userRepository.findByEmail(emailRecord.value());

        if (maybeCurrentUser.isEmpty()) {
            return Optional.empty();
        }

        final var currentUser = maybeCurrentUser.get();


        final var updatedUser = new UserModel(
            currentUser.id(),
            emailRecord,
            new Token(currentUser.token()), // TODO generate new token
            user.getUsername() == null
                ? new Username(currentUser.username())
                : new Username(user.getUsername()),
            user.getBio() == null
                ? Optional.of(new Bio(currentUser.bio()))
                : Optional.ofNullable(user.getBio()).map(Bio::new),
            user.getImage() == null
                ? Optional.of(new ImageReference(currentUser.image()))
                : Optional.ofNullable(user.getImage()).map(ImageReference::new)
        );

        final var savedUser = userRepository.save(updatedUser.toEntity());
        return Optional.of(savedUser).map(UserModel::fromEntity);
    }
}

