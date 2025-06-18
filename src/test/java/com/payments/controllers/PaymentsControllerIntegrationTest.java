package com.payments.controllers;

import com.payments.aop.TestsPopulationHelper;
import com.payments.controllers.dto.MakePaymentDTO;
import com.payments.repo.dao.CurrencyDao;
import com.payments.repo.dao.PaymentsDao;
import com.payments.repo.dao.TypeDao;
import com.payments.services.IntegrationTestConfig;
import com.payments.services.domain.entities.Currency;
import com.payments.services.domain.entities.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Stream;

import static com.payments.services.domain.entities.Currency.EUR;
import static com.payments.services.domain.entities.Currency.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(IntegrationTestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestsPopulationHelper popHelp;

    @Autowired
    private PaymentsDao paymentsDao;
    @Autowired
    private CurrencyDao currencyDao;
    @Autowired
    private TypeDao typeDao;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    void requirementsSetup() {

        var persistedCurrency1 = popHelp.persistCurrency(EUR.name());
        var persistedCurrency2 = popHelp.persistCurrency(USD.name());

        popHelp.persistType(Type.TYPE_1.name(), List.of(persistedCurrency1));
        popHelp.persistType(Type.TYPE_2.name(), List.of(persistedCurrency2));
        popHelp.persistType(Type.TYPE_3.name(), List.of(persistedCurrency1, persistedCurrency2));
    }


    private static Stream<Arguments> validValueTypeCombinations() {
        return Stream.of(
                Arguments.of(Type.TYPE_1, Currency.EUR),
                Arguments.of(Type.TYPE_2, Currency.USD),
                Arguments.of(Type.TYPE_3, Currency.EUR),
                Arguments.of(Type.TYPE_3, Currency.USD),
                Arguments.of(Type.TYPE_1, Currency.EUR),
                Arguments.of(Type.TYPE_2, Currency.USD)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "validValueTypeCombinations")
    void type1WorksWithEur(Type type, Currency currency) throws Exception {

        var sizeBeforeInsert = paymentsDao.findAll().size();

        var paymentDto = popHelp.makePaymentDTO(type, currency);

        var response = mockMvc.perform(post("/api/v1/payment")
                        .content(toJson(paymentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

//        System.out.println("**************************");
//        paymentsDao.findAll().forEach(System.out::println);
        assertEquals(sizeBeforeInsert + 1, paymentsDao.findAll().size());
        assertEquals( 3, typeDao.findAll().size());
        assertEquals( 2, currencyDao.findAll().size());


        System.out.println(response.andReturn().getResponse().getStatus());
        System.out.println(response.andReturn().getResponse().getContentAsString());
    }

    private String toJson(MakePaymentDTO dto) {
        try {
            return  objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
