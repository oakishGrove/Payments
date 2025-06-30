package com.payments.payment.controllers;

import com.payments.common.exceptions.PaymentCreationException;
import com.payments.payment.dto.MakePaymentDTO;
import com.payments.payment.services.PaymentsService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class PaymentsControllerUnitTest {

    @Mock
    private PaymentsService paymentsService;

    @InjectMocks
    private PaymentsController paymentsController;

    @Test
    void createdStatusWhenNoExceptionIsThrown() {

        doNothing().when(paymentsService).makePayment(any());

        var response = paymentsController.makePayment(new MakePaymentDTO());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void badRequestResponseWhenExceptionIsThrown() {

        doThrow(PaymentCreationException.class).when(paymentsService).makePayment(any());

        assertThrows(PaymentCreationException.class, () -> paymentsController.makePayment(new MakePaymentDTO()));
    }
}