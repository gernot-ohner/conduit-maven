package dev.ohner.conduit.repository;

import dev.ohner.conduit.repository.entity.TagEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends ListCrudRepository<TagEntity, UUID> {
}
