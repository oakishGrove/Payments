package com.payments.aop;

import com.payments.controllers.dto.MakePaymentDTO;
import com.payments.services.domain.entities.Currency;
import com.payments.services.domain.entities.Type;

public interface MakePaymentsDtoFactory {

    MakePaymentDTO validDto(Type type, Currency currency);
}
