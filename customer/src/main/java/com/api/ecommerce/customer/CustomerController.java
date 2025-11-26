package com.api.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private  final CustomerService service;



    @PostMapping
    public ResponseEntity<Integer> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ){
    return  ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping("/{customer-id}")
    public ResponseEntity<Integer> updateCustomer(
             @RequestBody @Valid CustomerRequest request
            , @PathVariable("customer-id") Integer customerId
    ) {

        return ResponseEntity.ok(this.service.updateCustomer(request,customerId));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(this.service.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(
            @PathVariable("customer-id") Integer customerId
    ) {
        return ResponseEntity.ok(this.service.existsById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(
            @PathVariable("customer-id") Integer customerId
    ) {
        return ResponseEntity.ok(this.service.findById(customerId));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("customer-id") Integer customerId
    ) {
        this.service.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }


}
