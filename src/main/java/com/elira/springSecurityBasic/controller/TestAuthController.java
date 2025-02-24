package com.elira.springSecurityBasic.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/method")
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
