package dev.ohner.conduit.controller;

import dev.ohner.conduit.repository.UserRepository;
import dev.ohner.conduit.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("conduit")
        .withUsername("conduit")
        .withPassword("test");

    @BeforeTransaction
    void setUp() {
        final var testUser = new UserEntity(
            null,
            "testuser@test.com",
            "{noop}password",
            "test_user",
            null,
            null
        );
        final var callerUser = new UserEntity(
            null,
            "calleruser@test.com",
            "{noop}password",
            "caller_user",
            null,
            null
        );
        userRepository.save(testUser);
        userRepository.save(callerUser);
    }

    @Test
    void createUser() throws Exception {

        final var email = "user.%s@gmail.com".formatted(UUID.randomUUID());
        final var userJSON = """
            {
                "user": {
                    "email": "%s",
                    "username": "test_user",
                    "password": "password"
                }
            }
            """.formatted(email);

        // 1. Create "user1"
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userJSON)
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.user.email").value(email));

        // 2. login as "user1"
        // 3. get the current user
        // 4. Update the current user
        // 5. As "user1", make a secured request
    }

}