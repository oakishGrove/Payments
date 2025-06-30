package com.payments.payment.domain.validation;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RulesValidator<R extends ValidationRule<T>, T> implements Validator<R, T>{

    private final List<R> validationRules;

    public RulesValidator(List<R> validationRules) {
        this.validationRules = validationRules;
    }

    @Override
    public Optional<List<String>> validate(T validate) {

        List<String> errorMessages = validationRules.stream()
                .filter(validationRule -> validationRule.applicable(validate))
                .map(validationRule -> validationRule.validate(validate))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        if (!errorMessages.isEmpty()) {
            return Optional.of(errorMessages);
        }

        return Optional.empty();
    }
}
