package com.api.ecommerce.notification;

import com.api.ecommerce.kafka.NotificationConsumer;
import com.api.ecommerce.kafka.payment.PaymentConfirmation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {
}
