package com.organization.crm.dtos.events;

import jakarta.validation.constraints.NotBlank;

public record EventCreateUpdateReq(
        @NotBlank(message = "Event name is required")
        String name,

        @NotBlank(message = "Event location is required")
        String location
) {
}
