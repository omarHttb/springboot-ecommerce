package com.api.ecommerce.Address;

import jakarta.validation.constraints.NotNull;

public record AddressRequest(
        Integer id,
        @NotNull(message = "Street is required")
        String street,
        @NotNull(message = "houseNumber is required")
        String houseNumber,
        @NotNull(message = "ZipCode is required")
        String zipCode,
        @NotNull(message = "CustomerId is required")
        Integer CustomerId
) {
}
