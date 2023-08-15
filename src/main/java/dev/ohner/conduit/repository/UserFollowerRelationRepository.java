package dev.ohner.conduit.repository;

import dev.ohner.conduit.repository.entity.UserFollowerRelationEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserFollowerRelationRepository extends ListCrudRepository<UserFollowerRelationEntity, UUID> {

    public List<UserFollowerRelationEntity> findFollowersByUserId(UUID userId);
}
