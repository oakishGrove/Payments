package com.payments.payment.domain.entities;

import java.util.List;

import static com.payments.payment.domain.entities.Currency.EUR;
import static com.payments.payment.domain.entities.Currency.USD;


public enum Type {
    TYPE_1(EUR),
    TYPE_2(USD),
    TYPE_3(EUR, USD);

    private List<Currency> supportedCurrencies;

    Type(Currency ... currency) {
        supportedCurrencies = List.of(currency);
    }

    public boolean isCurrencySupported(Currency currency) {
        return supportedCurrencies.contains(currency);
    }
}
