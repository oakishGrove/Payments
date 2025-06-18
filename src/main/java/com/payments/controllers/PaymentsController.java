package com.payments.controllers;

import com.payments.controllers.dto.MakePaymentDTO;
import com.payments.services.PaymentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.payments.controllers.Constants.API_V1;

@RestController
@RequestMapping(value = API_V1)
public class PaymentsController {

    private PaymentsService paymentsService;

    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @PostMapping("payment")
    public ResponseEntity makePayment(@RequestBody MakePaymentDTO paymentDTO) {

        paymentsService.makePayment(paymentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
