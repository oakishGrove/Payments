package com.payments.payment.aop;

import com.payments.payment.controllers.dto.MakePaymentDTO;
import com.payments.payment.services.domain.entities.Currency;
import com.payments.payment.services.domain.entities.Type;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MakePaymentsDtoFactoryImpl implements MakePaymentsDtoFactory {

    @Override
    public MakePaymentDTO validDto(Type type, Currency currency) {

        if (!type.isCurrencySupported(currency)) {
            throw new IllegalArgumentException("Currency doesn't support type");
        }

        var paymentDto = new MakePaymentDTO();
        paymentDto.setAmount(BigDecimal.ONE);
        paymentDto.setCurrency(currency);
        paymentDto.setType(type);
        paymentDto.setCreditorIban("ctr-iban");
        paymentDto.setDebtorIban("dbt-iban");

        switch (type) {
            case TYPE_1 -> paymentDto.setDetails("mandatory-details");
            case TYPE_2 -> paymentDto.setDetails("optional-details");
            case TYPE_3 -> paymentDto.setBic("mand-bic");
        }

        return paymentDto;
    }
}
