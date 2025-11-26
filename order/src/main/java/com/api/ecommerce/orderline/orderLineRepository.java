package com.api.ecommerce.orderline;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface orderLineRepository extends JpaRepository<OrderLines,Integer> {
    List<OrderLines> findAllByOrderId(Integer orderId);
}
