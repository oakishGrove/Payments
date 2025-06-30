package com.payments.common.validation.implementation;

import com.payments.common.validation.annotations.NotNullOrEmpty;
import com.payments.common.validation.validators.ValidateNotNullOrEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class NotNullOrEmptyValidator implements ConstraintValidator<NotNullOrEmpty, String> {

    private final ValidateNotNullOrEmpty validateNotNullOrEmpty;

    public NotNullOrEmptyValidator(ValidateNotNullOrEmpty validateNotNullOrEmpty) {
        this.validateNotNullOrEmpty = validateNotNullOrEmpty;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validateNotNullOrEmpty.validate(value);
    }
}
