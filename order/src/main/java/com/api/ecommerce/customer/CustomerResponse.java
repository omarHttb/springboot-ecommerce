package com.api.ecommerce.customer;

public record CustomerResponse(
        Integer id,
        String firstname,
        String lastname,
        String email
) {
}
