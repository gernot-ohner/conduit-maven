package dev.ohner.conduit.controller;

import dev.ohner.conduit.api.UserApi;
import dev.ohner.conduit.exception.UnauthorizedException;
import dev.ohner.conduit.exception.UnprocessableContentException;
import dev.ohner.conduit.model.GenericErrorModel;
import dev.ohner.conduit.model.GenericErrorModelErrors;
import dev.ohner.conduit.model.Login200Response;
import dev.ohner.conduit.model.UpdateCurrentUserRequest;
import dev.ohner.conduit.service.model.EmailRecord;
import dev.ohner.conduit.service.model.UserModel;
import dev.ohner.conduit.service.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Slf4j
@RestController
public class UserController implements UserApi {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
        operationId = "getCurrentUser",
        summary = "Get current user",
        description = "Gets the currently logged-in user",
        tags = { "User and Authentication" },
        responses = {
            @ApiResponse(responseCode = "200", description = "User", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Login200Response.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "422", description = "Unexpected error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class))
            })
        },
        security = {
            @SecurityRequirement(name = "Token")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/users",
        produces = { "application/json" }
    )
    public ResponseEntity<Login200Response> getCurrentUser(WebRequest request) {
        
        log.error("request {}", request);
        final var principal = request.getUserPrincipal();
        if (principal == null) {
            throw new UnauthorizedException("No principal found");
        }

        System.out.println("principal = " + principal);
        log.error("principal = " + principal);

        return userService
            .getUserByEmail(new EmailRecord(principal.getName())).map(UserModel::toUser)
            .map(Login200Response::new)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new UnprocessableContentException("No user found"));
    }

    @Override
    public ResponseEntity<Login200Response> updateCurrentUser(UpdateCurrentUserRequest body) {
        return UserApi.super.updateCurrentUser(body);
    }
}
