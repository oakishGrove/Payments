package com.payments.repo.dao;

import com.payments.repo.entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyDao extends JpaRepository<CurrencyEntity, Long> {

    Optional<CurrencyEntity> findByName(String name);
}
