package com.payments.common.validation.validators;

import org.springframework.stereotype.Component;

@Component
public class ValidateNotNullOrEmpty {

    public boolean validate(String str) {

        return str != null
                && !str.trim().isEmpty();
    }
}
