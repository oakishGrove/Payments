package com.payments.payment.domain.validation;

import java.util.List;
import java.util.Optional;

public interface Validator<R extends ValidationRule<T>, T> {

    Optional<List<String>> validate(T validate);
}
