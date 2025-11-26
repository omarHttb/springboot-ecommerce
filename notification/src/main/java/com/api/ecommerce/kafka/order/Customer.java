package com.api.ecommerce.kafka.order;

public record Customer(
        Integer id ,
        String firstName,
        String LastName,
        String email
) {

}
