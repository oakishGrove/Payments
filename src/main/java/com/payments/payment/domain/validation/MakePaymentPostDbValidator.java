//package com.payments.payment.domain.validation;
//
//import com.payments.payment.repo.entities.CurrencyEntity;
//import com.payments.payment.repo.entities.PaymentEntity;
//import com.payments.payment.repo.entities.TypeEntity;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class MakePaymentPostDbValidator implements Validator<MakePaymentPostDbValidationRule, PaymentEntity> {
//
//    private final List<MakePaymentPostDbValidationRule> rules;
//
//    public MakePaymentPostDbValidator(List<MakePaymentPostDbValidationRule> rules) {
//        this.rules = rules;
//    }
//
//    @Override
//    public Optional<List<String>> validate(PaymentEntity payment) {
//
//        if (!isCurrencyValidWithType(payment.getCurrency(), payment.getType())) {
//
//            return Optional.of(List.of("Specified type %s doesn't support this currency %s"
//                    .formatted(payment.getType().getName(), payment.getCurrency().getName())));
//        }
//
//        return Optional.empty();
//    }
//
//    private boolean isCurrencyValidWithType(CurrencyEntity expectedCurrency, TypeEntity type) {
//
//        if (expectedCurrency == null || type == null) {
//            return false;
//        }
//
//        return type.getCurrencyList().stream()
//                .map(CurrencyEntity::getName)
//                .anyMatch(currencyName -> currencyName.equals(expectedCurrency.getName()));
//    }
//}
