package dev.ohner.conduit.service;

import dev.ohner.conduit.repository.UserFollowerRelationRepository;
import dev.ohner.conduit.repository.UserRepository;
import dev.ohner.conduit.repository.entity.UserFollowerRelationEntity;
import dev.ohner.conduit.service.model.EmailRecord;
import dev.ohner.conduit.service.model.ProfileModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final UserFollowerRelationRepository userFollowerRelationRepository;

    public ProfileService(
        UserRepository userRepository,
        UserFollowerRelationRepository userFollowerRelationRepository
    ) {
        this.userRepository = userRepository;
        this.userFollowerRelationRepository = userFollowerRelationRepository;
    }

    public Optional<ProfileModel> getProfileByUsername(
        final String username,
        final Optional<EmailRecord> requestingUserEmail
    ) {
        final var maybeUser = userRepository.findByUsername(username);
        if (maybeUser.isEmpty()) {
            return Optional.empty();
        }
        final var user = maybeUser.get();

        final var follows = requestingUserEmail
            .flatMap(er -> userRepository.findByEmail(er.value()))
            .map(u -> userFollowerRelationRepository.existsByUserIdAndFollowerId(user.id(), u.id()))
            .orElse(false);

        return Optional.of(ProfileModel.fromUserEntity(user, follows));
    }

    public Optional<ProfileModel> followUserByUsername(String username, EmailRecord requestingEmail) {

        final var userToFollow = userRepository.findByUsername(username);
        if (userToFollow.isEmpty()) {
            return Optional.empty();
        }
        final var requestingUser = userRepository.findByEmail(requestingEmail.value());
        if (requestingUser.isEmpty()) {
            return Optional.empty();
        }

        userFollowerRelationRepository.save(
            new UserFollowerRelationEntity(
                null,
                userToFollow.get().id(),
                requestingUser.get().id(),
                OffsetDateTime.now()
            )
        );

        return Optional.of(ProfileModel.fromUserEntity(userToFollow.get(), true));
    }

    public Optional<ProfileModel> unfollowUserByUsername(String username, EmailRecord requestingEmail) {

        final var userToFollow = userRepository.findByUsername(username);
        if (userToFollow.isEmpty()) {
            return Optional.empty();
        }
        final var requestingUser = userRepository.findByEmail(requestingEmail.value());
        if (requestingUser.isEmpty()) {
            return Optional.empty();
        }

        userFollowerRelationRepository.deleteAllByUserIdAndFollowerId(
            userToFollow.get().id(),
            requestingUser.get().id()
        );

        return Optional.of(ProfileModel.fromUserEntity(userToFollow.get(), false));
    }
}
