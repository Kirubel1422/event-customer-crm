package com.organization.crm.dtos.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientRegister(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @Email(message = "Email is invaild.")
        @NotBlank
        String email,

        @Pattern(
                regexp = "^\\+?\\d{1,3}[-.\\s]?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$",
                message = "Invalid phone number format."
        )
        @NotBlank
        String phoneNumber,

        @NotBlank
        String plan,

        @NotBlank
        String eventId
) {
}
