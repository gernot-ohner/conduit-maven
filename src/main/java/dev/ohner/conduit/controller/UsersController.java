package dev.ohner.conduit.controller;

import dev.ohner.conduit.model.CreateUserRequest;
import dev.ohner.conduit.model.GenericErrorModel;
import dev.ohner.conduit.model.Login200Response;
import dev.ohner.conduit.model.LoginRequest;
import dev.ohner.conduit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UsersController {

    final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }


    /**
     * POST /users
     * Register a new user
     *
     * @param body Details of the new user to register (required)
     * @return User (status code 201)
     *         or Unexpected error (status code 422)
     */
    @Operation(
        operationId = "createUser",
        description = "Register a new user",
        tags = { "User and Authentication" },
        responses = {
            @ApiResponse(responseCode = "201", description = "User", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Login200Response.class))
            }),
            @ApiResponse(responseCode = "422", description = "Unexpected error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<Login200Response> createUser(
        @Parameter(name = "body", description = "Details of the new user to register", required = true) @Valid @RequestBody CreateUserRequest body
    ) {

        final var createdUser = userService.createUser(body.getUser()).toUser();
        final var login200Response = new Login200Response(createdUser);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(login200Response);
    }


    /**
     * POST /users/login : Existing user login
     * Login for existing user
     *
     * @param body Credentials to use (required)
     * @return User (status code 200)
     *         or Unauthorized (status code 401)
     *         or Unexpected error (status code 422)
     */
    @Operation(
        operationId = "login",
        summary = "Existing user login",
        description = "Login for existing user",
        tags = { "User and Authentication" },
        responses = {
            @ApiResponse(responseCode = "200", description = "User", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Login200Response.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "422", description = "Unexpected error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users/login",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<Login200Response> login(
        @Parameter(name = "body", description = "Credentials to use", required = true) @Valid @RequestBody LoginRequest body
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
