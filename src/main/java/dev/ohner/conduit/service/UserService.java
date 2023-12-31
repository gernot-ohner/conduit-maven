package dev.ohner.conduit.service;

import dev.ohner.conduit.model.NewUser;
import dev.ohner.conduit.model.UpdateUser;
import dev.ohner.conduit.repository.UserFollowerRelationRepository;
import dev.ohner.conduit.repository.UserRepository;
import dev.ohner.conduit.repository.entity.UserEntity;
import dev.ohner.conduit.service.model.*;
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
            user.getEmail() == null
                ? emailRecord
                : new EmailRecord(user.getEmail()),
            user.getPassword() == null
                ? Token.fromTokenString(currentUser.token())
                : Token.fromPassword(user.getPassword()),
            user.getUsername() == null
                ? new Username(currentUser.username())
                : new Username(user.getUsername()),
            user.getBio() == null
                ? Bio.ofNullable(currentUser.bio())
                : Bio.ofNullable(user.getBio()),
            user.getImage() == null
                ? ImageReference.ofNullable(currentUser.image())
                : ImageReference.ofNullable(user.getImage())
        );

        final var savedUser = userRepository.save(updatedUser.toEntity());
        return Optional.of(savedUser).map(UserModel::fromEntity);
    }


    public UserModel createUser(NewUser user) {

        final var newUserEntity = new UserEntity(
            null,
            new EmailRecord(user.getEmail()).value(),
            Token.fromPassword(user.getPassword()).value(),
            user.getUsername(),
            null,
            null
        );

        final var savedUser = userRepository.save(newUserEntity);
        return UserModel.fromEntity(savedUser);
    }
}

