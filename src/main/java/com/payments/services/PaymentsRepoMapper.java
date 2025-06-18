package com.payments.services;

import com.payments.aop.exceptions.controllers.PaymentCreationException;
import com.payments.controllers.dto.MakePaymentDTO;
import com.payments.repo.dao.CurrencyDao;
import com.payments.repo.dao.TypeDao;
import com.payments.repo.entities.PaymentEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PaymentsRepoMapper {

    private TypeDao typeDao;
    private CurrencyDao currencyDao;

    public PaymentsRepoMapper(TypeDao typeDao, CurrencyDao currencyDao) {
        this.typeDao = typeDao;
        this.currencyDao = currencyDao;
    }

    /**
     * Maps dto into entity with database look up without validations.
     *
     * @param dto
     * @return
     */

    @Transactional
    public PaymentEntity transformToEntity(MakePaymentDTO dto) {
        var paymentEntity = new PaymentEntity();

        paymentEntity.setAmount(dto.getAmount());
        paymentEntity.setDebtorIban(dto.getDebtorIban());
        paymentEntity.setCreditorIban(dto.getCreditorIban());
        paymentEntity.setBic(dto.getBic());

        var dbType = typeDao.findByName(dto.getType().name())
                .orElseThrow(() -> new PaymentCreationException("This type doesn't exist"));
        paymentEntity.setType(dbType);


        var dbCurrency = currencyDao.findByName(dto.getCurrency().name())
                .orElseThrow(() -> new PaymentCreationException("This currency doesn't exist"));
        paymentEntity.setCurrency(dbCurrency);

        return paymentEntity;
    }

}
