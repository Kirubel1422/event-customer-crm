package com.organization.crm.dtos;

import java.util.Set;

public record AuthResponse(
        String id,
        String email,
        Set<String> roles,
        String token
) {
}
