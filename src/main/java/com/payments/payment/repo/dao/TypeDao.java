package com.payments.payment.repo.dao;

import com.payments.payment.repo.entities.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeDao extends JpaRepository<TypeEntity, Long> {
    Optional<TypeEntity> findByName(String name);
}
