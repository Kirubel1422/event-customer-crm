package com.organization.crm.dtos.users;

import jakarta.validation.constraints.Email;

public record UpdateReq(
        @Email
        String email
) {
}
