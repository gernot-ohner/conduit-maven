package dev.ohner.conduit.service.model;

import dev.ohner.conduit.repository.UserRepository;
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
}

