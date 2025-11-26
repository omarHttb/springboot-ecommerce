package com.api.ecommerce.email;

import com.api.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.api.ecommerce.email.EmailTemplates.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender Mailsender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(String destinationEmail,
                                        String CustomerName,
                                        BigDecimal amount,
                                        String orderReference
    ) throws Exception{

        MimeMessage mimeMessage = Mailsender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        messageHelper.setFrom("omar.hattab37@Gmail.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();


        Map<String, Object> variables = new HashMap<>();
        variables.put("CustomerName", CustomerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);


        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());


        try {
            String html = templateEngine.process(templateName, context);
            messageHelper.setText(html, true);
            messageHelper.setTo(destinationEmail);
            Mailsender.send(mimeMessage);
            log.info(String.format("Email successfully sent to %s ", destinationEmail));
        } catch (MessagingException e) {
            log.warn("Error sending email to {}", destinationEmail);
        }


    }


    @Async
    public void SendOrderConfirmationEmail(String destinationEmail,
                                        String CustomerName,
                                        BigDecimal amount,
                                        String orderReference,
                                        List<Product> products
    ) throws Exception{

        MimeMessage mimeMessage = Mailsender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        messageHelper.setFrom("omar.hattab37@Gmail.com");
        final String templateName = ORDER_CONFIRMATION.getTemplate();


        Map<String, Object> variables = new HashMap<>();
        variables.put("CustomerName", CustomerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);


        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());


        try {
            String html = templateEngine.process(templateName, context);
            messageHelper.setText(html, true);
            messageHelper.setTo(destinationEmail);
            Mailsender.send(mimeMessage);
            log.info(String.format("Email successfully sent to %s ", destinationEmail));
        } catch (MessagingException e) {
            log.warn("Error sending email to {}", destinationEmail);
        }


    }
}
