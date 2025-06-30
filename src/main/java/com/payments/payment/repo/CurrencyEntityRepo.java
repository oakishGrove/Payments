package com.payments.payment.repo;

import com.payments.payment.repo.dao.CurrencyDao;
import com.payments.payment.repo.entities.CurrencyEntity;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
public class CurrencyEntityRepo {

    private final CurrencyDao currencyDao;

    public CurrencyEntityRepo(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    public CurrencyEntity getCurrencyByName(String name, Supplier<? extends RuntimeException> exceptionSupplier) {

        return currencyDao
                .findByName(name)
                .orElseThrow(exceptionSupplier);
    }
}
