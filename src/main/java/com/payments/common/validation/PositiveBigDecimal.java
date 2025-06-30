package com.payments.common.validation;

import com.payments.common.validation.implementation.PositiveBigDecimalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {PositiveBigDecimalValidator.class}
)
public @interface PositiveBigDecimal {

    String[] extensions() default {};

    String message() default "Number is not positive";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
