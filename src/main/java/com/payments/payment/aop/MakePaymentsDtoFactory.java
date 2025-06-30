package com.payments.payment.aop;

import com.payments.payment.domain.entities.Currency;
import com.payments.payment.domain.entities.Type;
import com.payments.payment.dto.MakePaymentDTO;

public interface MakePaymentsDtoFactory {

    MakePaymentDTO validDto(Type type, Currency currency);
}
