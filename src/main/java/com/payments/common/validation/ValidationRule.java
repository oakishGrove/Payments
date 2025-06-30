package com.payments.common.validation;

import java.util.Optional;

public interface ValidationRule<T> {

    Optional<String> validate(T target);
    
}
