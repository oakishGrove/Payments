package com.payments.aop;

import com.payments.aop.exceptions.controllers.PaymentCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PaymentCreationException.class)
    public ResponseEntity<String> handleResourceNotFoundException(PaymentCreationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        logger.error("Unhandled exception: " + ex.getMessage(), ex);

        return new ResponseEntity<>("Unexpected exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
