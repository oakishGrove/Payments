package com.payments.payment.domain.notifications;

import com.payments.payment.dto.MakePaymentDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificationPolicy {

    public Optional<String> notificationTarget(MakePaymentDTO makePaymentDTO) {

        return Optional.ofNullable(switch (makePaymentDTO.getType()) {
            case TYPE_1 -> "http://google.com:80";
            case TYPE_2 -> "http://reddit.com:80";
            default -> null;
        });
    }
}
