package com.payments.common.validation.validationrules;

import org.springframework.stereotype.Component;

@Component
public class ValidateNotNullOrEmpty {

    public boolean validate(String str) {

        return str != null
                && !str.trim().isEmpty();
    }
}
