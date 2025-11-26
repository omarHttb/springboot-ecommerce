package com.api.ecommerce.customer;

import com.api.ecommerce.Address.Address;
import com.api.ecommerce.Address.AddressResponse;

import java.util.List;

public record CustomerResponse(
        Integer id,

        String firstname,

        String lastname,

        String email,

        List<AddressResponse> addressResponses


) {



}
