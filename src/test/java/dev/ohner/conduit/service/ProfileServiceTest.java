package dev.ohner.conduit.service;

import dev.ohner.conduit.repository.UserFollowerRelationRepository;
import dev.ohner.conduit.repository.UserRepository;
import dev.ohner.conduit.repository.entity.UserEntity;
import dev.ohner.conduit.service.model.EmailRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserFollowerRelationRepository userFollowerRelationRepository;

    @InjectMocks
    private ProfileService underTest;


    @Test
    void getProfileByUsername_followsFalse_ifNoRelation() {

        final var id1 = UUID.randomUUID();
        final var userEntity = new UserEntity(
            id1,
            "username",
            "email",
            "password",
            "bio",
            null,
            "ROLE_USER"
        );
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));

        final var actual = underTest.getProfileByUsername("username", Optional.empty());

        assertThat(actual).isPresent();
        assertThat(actual.get().following()).isFalse();
        assertThat(actual.get().username().value()).isEqualTo(userEntity.username());
        assertThat(actual.get().bio()).isPresent();
        assertThat(actual.get().image()).isEmpty();
    }

    @Test
    void getProfileByUsername_followsFalse_ifNoRelevantRelation() {

        final var requestingUserEmail = new EmailRecord("test@google.com");
        final var userId = UUID.randomUUID();
        final var followerId = UUID.randomUUID();
        final var userEntity = new UserEntity(
            userId,
            "username",
            "email",
            "password",
            "bio",
            null,
            "ROLE_USER"
        );
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));
        when(userRepository.findByEmail(requestingUserEmail.value()))
            .thenReturn(Optional.of(new UserEntity(
                followerId,
                "other_username",
                "{noop}password",
                requestingUserEmail.value(),
                null,
                null,
                "ROLE_USER"
            )));
        when(userFollowerRelationRepository.existsByUserIdAndFollowerId(userId, followerId))
            .thenReturn(false);

        final var actual = underTest.getProfileByUsername("username",
            Optional.of(requestingUserEmail));

        assertThat(actual).isPresent();
        assertThat(actual.get().following()).isFalse();
        assertThat(actual.get().username().value()).isEqualTo(userEntity.username());
        assertThat(actual.get().bio()).isPresent();
        assertThat(actual.get().image()).isEmpty();
    }

    @Test
    void getProfileByUsername_followsTrue_ifRelevantRelation() {

        final var requestingUserEmail = new EmailRecord("test@google.com");
        final var userId = UUID.randomUUID();
        final var followerId = UUID.randomUUID();
        final var userEntity = new UserEntity(
            userId,
            "username",
            "email",
            "password",
            "bio",
            null,
            "ROLE_USER"
        );

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));
        when(userRepository.findByEmail(requestingUserEmail.value()))
            .thenReturn(Optional.of(new UserEntity(
                followerId,
                "other_username",
                "{noop}password",
                requestingUserEmail.value(),
                null,
                null,
                "ROLE_USER"
            )));
        when(userFollowerRelationRepository.existsByUserIdAndFollowerId(userId, followerId))
            .thenReturn(true);

        final var actual = underTest.getProfileByUsername("username",
            Optional.of(requestingUserEmail));

        assertThat(actual).isPresent();
        assertThat(actual.get().following()).isTrue();
        assertThat(actual.get().username().value()).isEqualTo(userEntity.username());
        assertThat(actual.get().bio()).isPresent();
        assertThat(actual.get().image()).isEmpty();
    }
}