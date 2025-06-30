package com.payments.payment.services;

import com.payments.payment.repo.dao.PaymentsDao;
import com.payments.payment.repo.entities.PaymentEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;

@Service
public class PaymentsEntityService {

    private final PaymentsDao paymentsDao;

    public PaymentsEntityService(PaymentsDao paymentsDao) {
        this.paymentsDao = paymentsDao;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void savePayment(PaymentEntity payment) {

        payment.setCreated(new Date(Instant.now().toEpochMilli()));
        paymentsDao.save(payment);
    }
}
