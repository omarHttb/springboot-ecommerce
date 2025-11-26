package com.api.ecommerce.order;

import com.api.ecommerce.customer.CustomerClient;
import com.api.ecommerce.exception.BusinessException;
import com.api.ecommerce.kafka.OrderConfirmation;
import com.api.ecommerce.kafka.OrderProducer;
import com.api.ecommerce.orderline.OrderLineRequest;
import com.api.ecommerce.orderline.OrderLineService;
import com.api.ecommerce.payment.PaymentClient;
import com.api.ecommerce.payment.PaymentRequest;
import com.api.ecommerce.product.ProductClient;
import com.api.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private  final CustomerClient customerClient;
    private  final ProductClient productClient;
    private  final OrderRepository repository;
    private  final OrderMapper mapper;
    private  final OrderLineService orderLineService;
    private  final OrderProducer orderProducer;
    private  final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest request) {
        //check the customer exist, will use openFeign
        var customer = this.customerClient.findCustomerById(request.customerId()).orElseThrow(
                () -> new BusinessException("Cannot create order:: No Customer Found with the ID: "+request.customerId())
        );

        //purchase the product --> product-ms (rest template)
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        //persist order

        var order = this.repository.save(mapper.toOrder(request));

        //persist order lines
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }


//          start the payment process
//        var paymentRequest = new PaymentRequest(
//                request.amount(),
//                request.paymentMethod(),
//                order.getId(),
//                order.getReference(),
//                customer
//        );
//        paymentClient.requestOrderPayment(paymentRequest);


        //send the order confirmation to notification ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(), request.amount(), request.paymentMethod(), customer, purchasedProducts
                )
        );


        return order.getId();
    }

    public List<OrderResponse> findAll() {

        return repository.findAll().stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {

        return  repository.findById(orderId).map(mapper::fromOrder).orElseThrow(() -> new EntityNotFoundException(String.format( "no order found with id: %d" + orderId )));
    }
}
