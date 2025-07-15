package com.payments.common.validation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValidationEngine<T> {

    private final List<? extends ValidationRule<T>> rules;

    public ValidationEngine(List<? extends ValidationRule<T>> rules) {
        this.rules = rules;
    }

    public Optional<List<String>> validate(T obj) {
        var errorsList = rules.stream()
//                .filter(rule -> rule.supports(obj))
                .map(rule -> rule.validate(obj))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return errorsList.isEmpty()
                    ? Optional.empty()
                    : Optional.of(errorsList);
    }

}
