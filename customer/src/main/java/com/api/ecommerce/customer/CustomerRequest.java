package com.api.ecommerce.customer;

import com.api.ecommerce.Address.AddressRequest;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        Integer id,

        @NotNull(message = "first name is required")
                String firstname,

        @NotNull(message = "last name is required")
                String lastname,

        @NotNull(message = "email is required")
                String email,
        @NotNull(message = "Address is required")
        AddressRequest address

) {
}
