package com.payments.payment.repo.dao;

import com.payments.payment.repo.entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyDao extends JpaRepository<CurrencyEntity, Long> {

    Optional<CurrencyEntity> findByName(String name);
}
