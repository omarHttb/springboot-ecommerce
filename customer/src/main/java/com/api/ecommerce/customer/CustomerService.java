package com.api.ecommerce.customer;

import com.api.ecommerce.Address.AddressService;
import com.api.ecommerce.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AddressService addressService;

    private final CustomerRepository repository;

    private final CustomerMapper mapper;



    public Integer createCustomer(CustomerRequest request) {
        var customer  = mapper.toCustomer(request);

        return repository.save(customer).getId();

    }

    public Integer updateCustomer(@Valid CustomerRequest request, Integer customerId) {
        Customer existingCustomer = repository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // We reuse mapper but set the correct id manually
        Customer updatedCustomer = mapper.toCustomer(request);
        updatedCustomer.setId(customerId);

      return   repository.save(updatedCustomer).getId();
    }

    public List<CustomerResponse> findAllCustomers() {
      return   repository.findAll().stream().map(mapper::toCustomerResponse).collect(Collectors.toList());
    }

    public Boolean existsById(Integer customerId) {
        if(customerId == null) return false;
        return repository.existsById(customerId);
    }

    public CustomerResponse findById(Integer customerId) {
        return repository.findById(customerId).map(mapper::toCustomerResponse).orElseThrow(() -> new CustomerNotFoundException(format("Customer not Found with the ID: %s"+  customerId)));
    }

    public void deleteCustomer(Integer customerId) {
        this.repository.deleteById(customerId);
    }
}
