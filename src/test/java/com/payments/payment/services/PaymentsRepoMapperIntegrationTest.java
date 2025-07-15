package com.payments.payment.services;

import com.payments.payment.aop.TestsPopulationHelper;
import com.payments.payment.repo.dao.TypeDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Import(IntegrationTestConfig.class)
class PaymentsRepoMapperIntegrationTest {

    @Autowired
    private TestsPopulationHelper popHelp;
    @Autowired
    private TypeDao typeDao;

    @Test
    void fetchDependenciesToReconstructFullEntity() {

        String TYPE_NAME = "type";
        String USD = "USD";
        String EUR = "EUR";

        var persistedCurrency1 = popHelp.persistCurrency(USD);
        var persistedCurrency2 = popHelp.persistCurrency(EUR);

        popHelp.persistType(TYPE_NAME, List.of(persistedCurrency1, persistedCurrency2));

        var type = typeDao.findByName(TYPE_NAME).get();

        assertEquals(TYPE_NAME, type.getName());
        assertEquals("USD", type.getCurrencyList().stream()
                .filter(c -> c.getName().equals(USD)).findAny().get().getName());
        assertEquals("EUR", type.getCurrencyList().stream()
                .filter(c -> c.getName().equals(EUR)).findAny().get().getName());
    }

}