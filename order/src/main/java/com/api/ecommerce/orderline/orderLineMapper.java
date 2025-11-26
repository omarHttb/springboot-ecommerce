package com.api.ecommerce.orderline;

import com.api.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class orderLineMapper {
    public OrderLines toOrderLine(OrderLineRequest request) {
        return OrderLines.builder()
                .id(request.id())
                .productId(request.productId())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLines orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getOrder().getId(),
                orderLine.getProductId(),
                orderLine.getQuantity()
        );    }
}
