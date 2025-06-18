package com.payments.repo.dao;

import com.payments.repo.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsDao extends JpaRepository<PaymentEntity, Long> {
}
