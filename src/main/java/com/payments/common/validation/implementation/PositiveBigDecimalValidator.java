package com.payments.common.validation.implementation;

import com.payments.common.validation.PositiveBigDecimal;
import com.payments.common.validation.validationrules.ValidatePositiveBigDecimal;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PositiveBigDecimalValidator implements ConstraintValidator<PositiveBigDecimal, BigDecimal> {

    private final ValidatePositiveBigDecimal positiveBigDecimal;

    public PositiveBigDecimalValidator(ValidatePositiveBigDecimal positiveBigDecimal) {

        this.positiveBigDecimal = positiveBigDecimal;
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {

        return positiveBigDecimal.valid(value);
    }
}
