package com.payments.payment.exceptions;

import com.payments.common.exceptions.PaymentCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

 @ControllerAdvice(basePackages = "com.payments.payment")
public class PaymentsExceptionHandling {

    private static final Logger logger = LoggerFactory.getLogger(com.payments.common.GlobalExceptionHandler.class);

    @ExceptionHandler(PaymentCreationException.class)
    public ResponseEntity<String> handleResourceNotFoundException(PaymentCreationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
