package dev.ohner.conduit.service.model;

import dev.ohner.conduit.exception.UnprocessableContentException;
import dev.ohner.conduit.model.UpdateUser;
import dev.ohner.conduit.repository.UserFollowerRelationRepository;
import dev.ohner.conduit.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;
    final UserFollowerRelationRepository userFollowerRelationRepository;

    public UserService(UserRepository userRepository, UserFollowerRelationRepository userFollowerRelationRepository) {
        this.userRepository = userRepository;
        this.userFollowerRelationRepository = userFollowerRelationRepository;
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

    public int getFollowerCount(EmailRecord emailRecord) throws UnprocessableContentException {
        final var user = userRepository.findByEmail(emailRecord.value())
            .orElseThrow(() -> new UnprocessableContentException("User not found"));

        return userFollowerRelationRepository.findFollowersByUserId(user.id()).size();
    }
}

