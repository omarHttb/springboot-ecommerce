package com.api.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private  final  orderLineRepository repository;
    private  final orderLineMapper mapper;
    public void saveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrderLine(request);
        repository.save(order);
    }

    public List<OrderLineResponse> FindAllOrderById(Integer orderId) {

        return repository.findAllByOrderId(orderId)
                .stream().map(mapper::toOrderLineResponse).collect(java.util.stream.Collectors.toList());
    }
}