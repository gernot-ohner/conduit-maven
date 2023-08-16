package dev.ohner.conduit.repository;

import dev.ohner.conduit.repository.entity.UserFollowerRelationEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface UserFollowerRelationRepository extends ListCrudRepository<UserFollowerRelationEntity, UUID> {

    boolean existsByUserIdAndFollowerId(UUID userId, UUID followerId);

    @Modifying
    @Query("DELETE FROM user_follower_relation WHERE user_id = :userId AND follower_id = :followerId")
    void deleteAllByUserIdAndFollowerId(UUID userId, UUID followerId);

}
