package dev.ohner.conduit.controller;

import dev.ohner.conduit.exception.UnauthorizedException;
import dev.ohner.conduit.exception.UnprocessableContentException;
import dev.ohner.conduit.model.GenericErrorModel;
import dev.ohner.conduit.model.Login200Response;
import dev.ohner.conduit.model.UpdateCurrentUserRequest;
import dev.ohner.conduit.service.model.EmailRecord;
import dev.ohner.conduit.service.model.UserModel;
import dev.ohner.conduit.service.model.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestController
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * GET /user : Get current user
     * Gets the currently logged-in user
     *
     * @return User (status code 200)
     * or Unauthorized (status code 401)
     * or Unexpected error (status code 422)
     */
    @Operation(
        operationId = "getCurrentUser",
        summary = "Get current user",
        description = "Gets the currently logged-in user",
        tags = {"User and Authentication"},
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
        value = "/user",
        produces = {"application/json"}
    )
    public ResponseEntity<Login200Response> getCurrentUser(
        final WebRequest request
    ) throws UnauthorizedException, UnprocessableContentException {

        final var principalEmail = getPrincipalEmail(request);

        final var followerCount = userService.getFollowerCount(principalEmail);
        log.error("Follower count: {}", followerCount);

        return userService
            .getUserByEmail(principalEmail)
            .map(UserModel::toUser)
            .map(Login200Response::new)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new UnprocessableContentException("No user found"));
    }

    /**
     * PUT /user : Update current user
     * Updated user information for current user
     *
     * @param body User details to update. At least **one** field is required. (required)
     * @return User (status code 200)
     * or Unauthorized (status code 401)
     * or Unexpected error (status code 422)
     */
    @Operation(
        operationId = "updateCurrentUser",
        summary = "Update current user",
        description = "Updated user information for current user",
        tags = {"User and Authentication"},
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
        method = RequestMethod.PUT,
        value = "/user",
        produces = {"application/json"},
        consumes = {"application/json"}
    )
    public ResponseEntity<Login200Response> updateCurrentUser(
        final WebRequest request,
        @Parameter(name = "body", description = "User details to update. At least **one** field is required.", required = true)
        @Valid @RequestBody UpdateCurrentUserRequest body
    ) throws UnauthorizedException, UnprocessableContentException {

        final var principal = getPrincipalEmail(request);

        return userService
            .updateUserByEmail(principal, body.getUser())
            .map(UserModel::toUser)
            .map(Login200Response::new)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new UnprocessableContentException("No user found"));
    }

    private static EmailRecord getPrincipalEmail(WebRequest request) throws UnauthorizedException {
        log.info("Handling request: {}", request);
        final var principal = request.getUserPrincipal();
        if (principal == null) {
            throw new UnauthorizedException("No principal found");
        }

        return new EmailRecord(principal.getName());
    }
}
