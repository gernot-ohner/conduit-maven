package dev.ohner.conduit.service;

import dev.ohner.conduit.repository.UserFollowerRelationRepository;
import dev.ohner.conduit.repository.UserRepository;
import dev.ohner.conduit.service.model.EmailRecord;
import dev.ohner.conduit.service.model.ProfileModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        final boolean follows;
        if (requestingUserEmail.isEmpty()) {
            follows = false;
        } else {
            final var email = requestingUserEmail.get().value();
            final var requestingUser = userRepository.findByEmail(email);
            if (requestingUser.isEmpty()) {
                follows = false;
            } else {
                final var requestingUserId = requestingUser.get().id();
                follows = userFollowerRelationRepository.existsByUserIdAndFollowerId(user.id(), requestingUserId);
            }
        }

        return Optional.of(ProfileModel.fromUserEntity(user, follows));
    }
}
