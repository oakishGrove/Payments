package com.payments.payment.domain.validation;

import java.util.Optional;

public interface ValidationRule<T> {

    /**
     * @param validationTarget
     * @return Wrapped string which contains failure reason
     */
    Optional<String> validate(T validationTarget);

    default boolean applicable(T validationTarget) {
        return true;
    }
}
