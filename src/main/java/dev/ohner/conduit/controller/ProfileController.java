package dev.ohner.conduit.controller;

import dev.ohner.conduit.exception.UnprocessableContentException;
import dev.ohner.conduit.model.GenericErrorModel;
import dev.ohner.conduit.model.GetProfileByUsername200Response;
import dev.ohner.conduit.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * POST /profiles/{username}/follow : Follow a user
     * Follow a user by username
     *
     * @param username Username of the profile you want to follow (required)
     * @return Profile (status code 200)
     * or Unauthorized (status code 401)
     * or Unexpected error (status code 422)
     */
    @Operation(
        operationId = "followUserByUsername",
        summary = "Follow a user",
        description = "Follow a user by username",
        tags = {"Profile"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Profile", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GetProfileByUsername200Response.class))
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
        method = RequestMethod.POST,
        value = "/profiles/{username}/follow",
        produces = {"application/json"}
    )
    public ResponseEntity<GetProfileByUsername200Response> followUserByUsername(
        final WebRequest request,
        @Parameter(name = "username", description = "Username of the profile you want to follow", required = true, in = ParameterIn.PATH)
        @PathVariable("username") String username
    ) {
        return null;
    }

    /**
     * GET /profiles/{username} : Get a profile
     * Get a profile of a user of the system. Auth is optional
     *
     * @param username Username of the profile to get (required)
     * @return Profile (status code 200)
     * or Unauthorized (status code 401)
     * or Unexpected error (status code 422)
     */
    @Operation(
        operationId = "getProfileByUsername",
        summary = "Get a profile",
        description = "Get a profile of a user of the system. Auth is optional",
        tags = {"Profile"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Profile", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GetProfileByUsername200Response.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "422", description = "Unexpected error", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GenericErrorModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/profiles/{username}",
        produces = {"application/json"}
    )
    public ResponseEntity<GetProfileByUsername200Response> getProfileByUsername(
        final WebRequest request,
        @Parameter(name = "username", description = "Username of the profile to get", required = true, in = ParameterIn.PATH)
        @PathVariable("username") String username
    ) throws UnprocessableContentException {

        final var requestingUserEmail = Utils.getOptionalPrincipalEmail(request);

        return profileService.getProfileByUsername(username, requestingUserEmail)
            .map(dev.ohner.conduit.service.model.ProfileModel::toProfile)
            .map(GetProfileByUsername200Response::new)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new UnprocessableContentException("Could not transform profile to response"));
    }

    /**
     * DELETE /profiles/{username}/follow : Unfollow a user
     * Unfollow a user by username
     *
     * @param username Username of the profile you want to unfollow (required)
     * @return Profile (status code 200)
     * or Unauthorized (status code 401)
     * or Unexpected error (status code 422)
     */
    @Operation(
        operationId = "unfollowUserByUsername",
        summary = "Unfollow a user",
        description = "Unfollow a user by username",
        tags = {"Profile"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Profile", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GetProfileByUsername200Response.class))
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
        method = RequestMethod.DELETE,
        value = "/profiles/{username}/follow",
        produces = {"application/json"}
    )
    public ResponseEntity<GetProfileByUsername200Response> unfollowUserByUsername(
        final WebRequest request,
        @Parameter(name = "username", description = "Username of the profile you want to unfollow", required = true, in = ParameterIn.PATH)
        @PathVariable("username") String username
    ) {
        return null;
    }
}
