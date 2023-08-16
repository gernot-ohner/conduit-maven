package dev.ohner.conduit.controller;

import dev.ohner.conduit.repository.UserRepository;
import dev.ohner.conduit.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class ProfileControllerTest {

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
    @WithMockUser("calleruser@test.com")
    void fullFlow() throws Exception {

        final var testUser = "test_user";

        // 1. Retrieve the profile (user should not be followed yet)
        mockMvc.perform(get("/profiles/" + testUser))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profile.following").value(false));

        // 2. Follow the user
        mockMvc.perform(post("/profiles/" + testUser + "/follow"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profile.following").value(true));

        // 3. Retrieve the profile (user should be followed now)
        mockMvc.perform(get("/profiles/" + testUser))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profile.following").value(true));

        // 4. Unfollow the user
        mockMvc.perform(delete("/profiles/" + testUser + "/follow"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profile.following").value(false));

        // 5. Retrieve the profile (user should not be followed anymore)
        mockMvc.perform(get("/profiles/" + testUser))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profile.following").value(false));
    }

}