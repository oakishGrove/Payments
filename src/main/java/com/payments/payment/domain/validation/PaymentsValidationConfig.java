//package com.payments.payment.domain.validation;
//
//import com.payments.payment.domain.validation.MakePaymentPreDb;
//import com.payments.common.validation.ValidationEngine;
//import com.payments.payment.dto.MakePaymentDTO;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.LinkedList;
//
//@Configuration
//public class PaymentsValidationConfig {
//
//    @Bean
//    public ValidationEngine<MakePaymentDTO> makePaymentPreDatabaseChecks(LinkedList<MakePaymentPreDb> rules) {
//
//        return new ValidationEngine<>(rules);
//    }
//}
