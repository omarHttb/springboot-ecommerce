package com.api.ecommerce.Address;


public record AddressResponse(
        Integer id,
        String street,
        String houseNumber,
        String ZipCode
) {
}
