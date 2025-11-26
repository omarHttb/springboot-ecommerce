package com.api.ecommerce.Address;

import com.api.ecommerce.customer.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepsitory repository;
    private final AddressMapper mapper;


    public Integer createAddress(AddressRequest request) {
        var address  = mapper.toAddress(request);
        return repository.save(address).getId();
    }
}
