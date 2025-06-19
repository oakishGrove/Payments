package com.payments.payment.aop.exceptions.controllers;

public class PaymentCreationException extends RuntimeException {

    public PaymentCreationException(String invalidAmountProvided) {
        super(invalidAmountProvided);
    }
}
