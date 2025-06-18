package com.payments.aop;

import com.payments.controllers.dto.MakePaymentDTO;
import com.payments.repo.dao.CurrencyDao;
import com.payments.repo.dao.TypeDao;
import com.payments.repo.entities.CurrencyEntity;
import com.payments.repo.entities.TypeEntity;
import com.payments.services.domain.entities.Currency;
import com.payments.services.domain.entities.Type;
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
