package com.elira.springSecurityBasic.controller;

import com.elira.springSecurityBasic.controller.response.AuthResponse;
import com.elira.springSecurityBasic.controller.request.AuthCreateUserRequest;
import com.elira.springSecurityBasic.controller.request.AuthLoginRequest;
import com.elira.springSecurityBasic.service.UserDetailServiceImpl;
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
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/log-in")
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
