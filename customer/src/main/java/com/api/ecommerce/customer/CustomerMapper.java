package com.api.ecommerce.customer;

import com.api.ecommerce.Address.Address;
import com.api.ecommerce.Address.AddressResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class CustomerMapper {



    public Customer toCustomer(CustomerRequest request){
        Customer customer = Customer.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .build();

        // Map address if present
        if (request.address() != null) {
            Address address = Address.builder()
                    .id(request.address().id())
                    .street(request.address().street())
                    .houseNumber(request.address().houseNumber())
                    .zipCode(request.address().zipCode())
                    .customer(customer)
                    .build();

            List<Address> addresses = new ArrayList<>();
            addresses.add(address);
            customer.setAddresses(addresses);
        }

        return customer;
    }
    public CustomerResponse toCustomerResponse(Customer customer) {
        List<AddressResponse> addresses = customer.getAddresses().stream()
                .map(address -> new AddressResponse(
                        address.getId(),
                        address.getStreet(),
                        address.getHouseNumber(),
                        address.getZipCode()
                ))
                .toList();

        return  new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                addresses
        );
    }
}

