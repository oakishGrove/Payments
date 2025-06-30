package com.payments.payment.domain.validation;

import com.payments.common.validation.ValidationEngine;
import com.payments.payment.dto.MakePaymentDTO;
import com.payments.payment.repo.entities.PaymentEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ValidationConfig {

    @Bean
    public ValidationEngine<MakePaymentDTO> makePaymentValidator(List<MakePaymentPreDb> rules) {

        return new ValidationEngine<>(rules);
    }

    @Bean
    public ValidationEngine<PaymentEntity> makePaymentPostDbValidator(List<MakePaymentPostDbRules> rules) {

        return new ValidationEngine<>(rules);
    }
}
