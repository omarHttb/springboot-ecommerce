package com.api.ecommerce.payment;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        Integer Id,
        @NotNull(message = "First name is required")
        String FirstName,
        @NotNull(message = "Last name is required")
        String LastName,
        @NotNull(message = "Email is required")
        @Email(message = "Email not correctly formatted")
        String Email
) {
}
