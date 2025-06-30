package com.payments.common.exceptions;

import java.util.List;

public class PaymentCreationException extends RuntimeException {

    private final List<String> reasons;

    public PaymentCreationException(String message) {
        super(message);
        reasons = List.of(message);
    }

    public PaymentCreationException(List<String> reasons) {
//        super(invalidAmountProvided);
        this.reasons = reasons;
    }

    public List<String> getReasons() {
        return reasons;
    }
}
