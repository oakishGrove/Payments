package com.payments.payment.repo.dao;

import com.payments.payment.repo.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsDao extends JpaRepository<PaymentEntity, Long> {
}
