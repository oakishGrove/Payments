//package com.payments.payment.domain.validation;
//
//import com.payments.payment.dto.MakePaymentDTO;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class MakePaymentPreDBValidator implements Validator<MakePaymentPreDBValidationRule, MakePaymentDTO> {
//
//    private final List<MakePaymentPreDBValidationRule> validationRules;
//
//    public MakePaymentPreDBValidator(List<MakePaymentPreDBValidationRule> validationRules) {
//        this.validationRules = validationRules;
//    }
//
//    public Optional<List<String>> validate(MakePaymentDTO makePaymentDTO) {
//
//        List<String> errorMessages = validationRules.stream()
//                .filter(validationRule -> validationRule.applicable(makePaymentDTO))
//                .map(validationRule -> validationRule.validate(makePaymentDTO))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .toList();
//
//        if (!errorMessages.isEmpty()) {
//            return Optional.of(errorMessages);
//        }
//
//        return Optional.empty();
//    }
//}
//
///*
//  Validator userLoginValidator = Validator.forInstance(UserLogin.class, (login, errors) -> {
//            ValidationUtils.
//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "field.required");
//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
//            if (login.getPassword() != null
//                    && login.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
//                errors.rejectValue("password", "field.min.length",
//                        new Object[]{Integer.valueOf(MINIMUM_PASSWORD_LENGTH)},
//                        "The password must be at least [" + MINIMUM_PASSWORD_LENGTH + "] characters in length.");
//            }
//        });
// */