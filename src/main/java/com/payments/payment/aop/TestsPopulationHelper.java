package com.payments.payment.aop;

import com.payments.payment.controllers.dto.MakePaymentDTO;
import com.payments.payment.repo.dao.CurrencyDao;
import com.payments.payment.repo.dao.TypeDao;
import com.payments.payment.repo.entities.CurrencyEntity;
import com.payments.payment.repo.entities.TypeEntity;
import com.payments.payment.services.domain.entities.Currency;
import com.payments.payment.services.domain.entities.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestsPopulationHelper {

    @Autowired
    private TypeDao typeDao;
    @Autowired
    private CurrencyDao currencyDao;
    @Autowired
    private MakePaymentsDtoFactory factory;


    public TestsPopulationHelper(TypeDao typeDao, CurrencyDao currencyDao, MakePaymentsDtoFactory factory) {
        this.typeDao = typeDao;
        this.currencyDao = currencyDao;
        this.factory = factory;
    }

    public TypeEntity persistType(String name, List<CurrencyEntity> currencyList) {

        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setName(name);
        typeEntity.setCurrency(currencyList);
        return typeDao.save(typeEntity);
    }

    public CurrencyEntity persistCurrency(String name) {

        CurrencyEntity currency = new CurrencyEntity();
        currency.setName(name);
        return currencyDao.save(currency);
    }

    public MakePaymentDTO makePaymentDTO(Type type, Currency currency) {

        return factory.validDto(type, currency);
    }
}
