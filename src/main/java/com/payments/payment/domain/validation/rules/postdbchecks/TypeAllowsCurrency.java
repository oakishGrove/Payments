package com.payments.payment.domain.validation.rules.postdbchecks;

import com.payments.payment.domain.validation.MakePaymentPostDbRules;
import com.payments.payment.repo.entities.CurrencyEntity;
import com.payments.payment.repo.entities.PaymentEntity;
import com.payments.payment.repo.entities.TypeEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TypeAllowsCurrency implements MakePaymentPostDbRules {


    @Override
    public Optional<String> validate(PaymentEntity payment) {

        System.out.println("POST VALIDATION CALLED");

        if (!isCurrencyValidWithType(payment.getCurrency(), payment.getType())) {

            return Optional.of("Specified type %s doesn't support this currency %s"
                    .formatted(payment.getType().getName(), payment.getCurrency().getName()));
        }

        return Optional.empty();
    }

    private boolean isCurrencyValidWithType(CurrencyEntity expectedCurrency, TypeEntity type) {

        if (expectedCurrency == null || type == null) {
            return false;
        }

        return type.getCurrencyList().stream()
                .map(CurrencyEntity::getName)
                .anyMatch(currencyName -> currencyName.equals(expectedCurrency.getName()));
    }
}
