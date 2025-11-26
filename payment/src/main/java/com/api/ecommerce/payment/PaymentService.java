package com.api.ecommerce.payment;

import com.api.ecommerce.notification.NotificationProducer;
import com.api.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final  PaymentRepository repository;
    private final  PaymentMapper mapper;
    private final NotificationProducer producer;

    public Integer createPayment(PaymentRequest request){
        var payment = repository.save(mapper.toPayment(request));

        producer.sendPaymentNotification(new PaymentNotificationRequest
                (request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().FirstName(),
                        request.customer().LastName(),
                        request.customer().Email())
        );

        return payment.getId();
    }
}
