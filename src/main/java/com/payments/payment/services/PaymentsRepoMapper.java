package com.payments.payment.services;

import com.payments.payment.dto.MakePaymentDTO;
import com.payments.payment.repo.entities.CurrencyEntity;
import com.payments.payment.repo.entities.PaymentEntity;
import com.payments.payment.repo.entities.TypeEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentsRepoMapper {

    public PaymentEntity transformToEntity(MakePaymentDTO dto, TypeEntity type, CurrencyEntity currency) {
        var paymentEntity = new PaymentEntity();

        paymentEntity.setAmount(dto.getAmount());
        paymentEntity.setDebtorIban(dto.getDebtorIban());
        paymentEntity.setCreditorIban(dto.getCreditorIban());
        paymentEntity.setBic(dto.getBic());
        paymentEntity.setType(type);
        paymentEntity.setCurrency(currency);

        return paymentEntity;
    }
}
