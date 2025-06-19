package com.payments.payment.aop;

import com.payments.payment.controllers.dto.MakePaymentDTO;
import com.payments.payment.services.domain.entities.Currency;
import com.payments.payment.services.domain.entities.Type;

public interface MakePaymentsDtoFactory {

    MakePaymentDTO validDto(Type type, Currency currency);
}
