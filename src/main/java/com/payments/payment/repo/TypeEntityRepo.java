package com.payments.payment.repo;

import com.payments.payment.repo.dao.TypeDao;
import com.payments.payment.repo.entities.TypeEntity;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
public class TypeEntityRepo {

    private final TypeDao typeDao;

    public TypeEntityRepo(TypeDao typeDao) {
        this.typeDao = typeDao;
    }

    public TypeEntity getTypeByName(String name, Supplier<? extends RuntimeException> exceptionSupplier) {
        return typeDao
                .findByName(name)
                .orElseThrow(exceptionSupplier);
    }
}
