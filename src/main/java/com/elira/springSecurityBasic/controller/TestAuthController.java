package com.elira.springSecurityBasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TestAuthController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello world!";
    }

    @GetMapping("/hello-secured")
    public String helloSecured() {
        return "Hello world Secured!";
    }

    @GetMapping("/hello-secured-two")
    public String helloSecuredTwo() {
        return "Hello world Secured Two!";
    }
}
