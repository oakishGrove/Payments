package com.payments.aop.exceptions.controllers;

public class PaymentCreationException extends RuntimeException {

    public PaymentCreationException(String invalidAmountProvided) {
        super(invalidAmountProvided);
    }
}
