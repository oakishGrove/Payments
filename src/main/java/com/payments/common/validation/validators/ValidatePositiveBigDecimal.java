package com.payments.common.validation.validators;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidatePositiveBigDecimal {

    public boolean valid(BigDecimal positive) {

        return positive != null && positive.compareTo(BigDecimal.ZERO) > 0;
    }
}
