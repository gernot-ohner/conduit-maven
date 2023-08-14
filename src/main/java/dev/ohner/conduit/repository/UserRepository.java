package dev.ohner.conduit.repository;

import dev.ohner.conduit.service.model.EmailRecord;
import dev.ohner.conduit.repository.entity.UserEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<UserEntity, EmailRecord> {
}
