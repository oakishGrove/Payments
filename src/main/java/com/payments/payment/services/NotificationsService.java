package com.payments.payment.services;

import com.payments.notifications.NotificationsFacade;
import com.payments.payment.domain.notifications.NotificationPolicy;
import com.payments.payment.dto.MakePaymentDTO;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    private final NotificationsFacade notificationsFacade;
    private final NotificationPolicy notificationPolicy;

    public NotificationsService(NotificationsFacade notificationsFacade, NotificationPolicy notificationPolicy) {
        this.notificationsFacade = notificationsFacade;
        this.notificationPolicy = notificationPolicy;
    }

    public void sendNotification(MakePaymentDTO payment) {

        notificationPolicy
                .notificationTarget(payment)
                .ifPresent(url -> notificationsFacade.sendNotification(url, payment.getType().name()));
    }
}
