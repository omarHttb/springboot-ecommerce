package com.api.ecommerce.kafka;

import com.api.ecommerce.email.EmailService;
import com.api.ecommerce.kafka.order.OrderConfirmation;
import com.api.ecommerce.kafka.payment.PaymentConfirmation;
import com.api.ecommerce.kafka.payment.PaymentMethod;
import com.api.ecommerce.notification.Notification;
import com.api.ecommerce.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.api.ecommerce.notification.NotificationType.ORDER_CONFIRMATION;
import static com.api.ecommerce.notification.NotificationType.PAYMENT_CONFIRMATION;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository repository;
    private final EmailService emailService;


    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmation paymentConfirmation) throws Exception {
        log.info(format("Consuming the message from payment-topic Topic:: %s",paymentConfirmation));
        repository.save(Notification.builder()
                        .notificationType(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build());
        //  send email
        var customerFullName = paymentConfirmation.customerFirstName() + paymentConfirmation.CustomerLastName();

        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerFullName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference());
    }


    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotifications(OrderConfirmation orderConfirmation) throws Exception {
        log.info(format("Consuming the message from order-topic Topic:: %s",orderConfirmation));
        repository.save(Notification.builder()
                .notificationType(ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build());


        var customerFullName = orderConfirmation.customer().firstName() + orderConfirmation.customer().LastName();

        emailService.SendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerFullName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()

        );
    }

}
