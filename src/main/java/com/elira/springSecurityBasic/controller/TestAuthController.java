package com.elira.springSecurityBasic.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/method")
@Tag(
        name = "Test",
        description = "Test the endpoints base on the role, permission and the token"
)
public class TestAuthController {

    @GetMapping("/get")
    public String helloGet() {
        return "Hello Get";
    }

    @PostMapping("/post")
    public String helloPost() {
        return "Hello Post";
    }

    @PutMapping("/put")
    public String helloPut() {
        return "Hello Put";
    }

    @DeleteMapping("/delete")
    public String helloDelete() {
        return "Hello Delete";
    }

    @PatchMapping("/patch")
    public String helloPatch() {
        return "Hello Patch";
    }
}
