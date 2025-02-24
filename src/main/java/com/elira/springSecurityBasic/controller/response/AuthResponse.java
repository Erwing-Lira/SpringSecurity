package com.elira.springSecurityBasic.controller.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jtw", "status"})
public record AuthResponse(
        String username,
        String message,
        String jtw,
        boolean status
) {
}
