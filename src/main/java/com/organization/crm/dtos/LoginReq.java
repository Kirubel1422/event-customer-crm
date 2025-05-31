package com.organization.crm.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginReq(
        @Email
        String email,

        @NotBlank
        String password
) {
}
