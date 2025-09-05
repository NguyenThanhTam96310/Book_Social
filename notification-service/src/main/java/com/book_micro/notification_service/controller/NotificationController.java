package com.book_micro.notification_service.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.book_micro.event.dto.NotificationEvent;
import com.book_micro.notification_service.dto.request.Recipient;
import com.book_micro.notification_service.dto.request.SendEmailRequest;
import com.book_micro.notification_service.service.EmailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {

    EmailService emailService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotification(NotificationEvent message) {
        log.info("Message receives {}", message);
        emailService.sendEmail(SendEmailRequest.builder()
                .to(Recipient.builder().email(message.getRecipient()).build())
                .subject(message.getSubject())
                .htmlContent(message.getBoby())
                .build());
    }
}
