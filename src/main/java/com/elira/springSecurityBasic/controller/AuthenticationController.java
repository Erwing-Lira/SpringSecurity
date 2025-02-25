package com.elira.springSecurityBasic.controller;

import com.elira.springSecurityBasic.controller.response.AuthResponse;
import com.elira.springSecurityBasic.controller.request.AuthCreateUserRequest;
import com.elira.springSecurityBasic.controller.request.AuthLoginRequest;
import com.elira.springSecurityBasic.service.UserDetailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Tag(
        name = "Authentication",
        description = "Log-in and Sign-up to be able to get the JWT token"
)
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/log-in")
    @Operation(
            summary = "Log-in user",
            description = "Authenticate a user and return with JWT token",
            tags = {"Authentication"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authentication request with username and password",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = AuthLoginRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful authentication",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = AuthResponse.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid AuthLoginRequest userRequest
    ) {
        return new ResponseEntity<>(
                this.userDetailService.loginUser(userRequest),
                HttpStatus.OK
        );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(
            @RequestBody @Valid AuthCreateUserRequest authCreateUserRequest
    ) {
        return new ResponseEntity<>(
                this.userDetailService.createUser(authCreateUserRequest),
                HttpStatus.CREATED
        );
    }
}
