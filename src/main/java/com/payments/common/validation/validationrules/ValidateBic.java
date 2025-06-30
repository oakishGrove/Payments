package com.payments.common.validation.validationrules;

import org.springframework.stereotype.Component;

@Component
public class ValidateBic {

    private final ValidateNotNullOrEmpty notNullOrEmpty;

    public ValidateBic(ValidateNotNullOrEmpty notNullOrEmpty) {
        this.notNullOrEmpty = notNullOrEmpty;
    }

    public boolean valid(String bic) {

        return notNullOrEmpty.validate(bic);
    }
}
