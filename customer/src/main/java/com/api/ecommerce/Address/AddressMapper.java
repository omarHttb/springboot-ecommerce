package com.api.ecommerce.Address;

import com.api.ecommerce.customer.Customer;
import org.springframework.stereotype.Service;


@Service
public class AddressMapper {

    public Address toAddress(AddressRequest request){
        return Address.builder()
                .id(request.id())
                .street(request.street())
                .houseNumber(request.houseNumber())
                .zipCode(request.zipCode())
                .build();
    }

    public AddressResponse toAddressResponse(Address address) {
        return  new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getZipCode()
        );
    }
}
