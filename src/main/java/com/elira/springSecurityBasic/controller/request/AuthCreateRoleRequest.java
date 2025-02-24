package com.elira.springSecurityBasic.controller.request;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record AuthCreateRoleRequest(
        @Size(max = 3, message = "User cannot have more than 3 roles") List<String> rolesName
) {
}
