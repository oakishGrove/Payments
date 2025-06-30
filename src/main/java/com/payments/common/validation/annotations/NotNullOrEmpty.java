package com.payments.common.validation.annotations;

import com.payments.common.validation.implementation.NotNullOrEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {NotNullOrEmptyValidator.class}
)
public @interface NotNullOrEmpty {

    String[] extensions() default {};

    String message() default "Field is empty or not defined";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
