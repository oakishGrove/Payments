package com.payments.payment.controllers;

import com.payments.notifications.NotificationsFacade;
import com.payments.notifications.repo.NotificationAttemptDao;
import com.payments.notifications.repo.NotificationAttemptEntity;
import com.payments.payment.aop.TestsPopulationHelper;
import com.payments.payment.domain.entities.Currency;
import com.payments.payment.domain.entities.Type;
import com.payments.payment.dto.MakePaymentDTO;
import com.payments.payment.repo.dao.CurrencyDao;
import com.payments.payment.repo.dao.PaymentsDao;
import com.payments.payment.repo.dao.TypeDao;
import com.payments.payment.services.IntegrationTestConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Stream;

import static com.payments.payment.domain.entities.Currency.EUR;
import static com.payments.payment.domain.entities.Currency.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private NotificationsFacade notificationsFacade;
    @Autowired
    private NotificationAttemptDao notificationDao;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setupNotifications() {

        notificationsFacade.initService();
    }

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
                Arguments.of(Type.TYPE_3, Currency.USD)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "validValueTypeCombinations")
    void typeWorksWithCurrency(Type type, Currency currency) throws Exception {

        var sizeBeforeInsert = paymentsDao.findAll().size();

        var paymentDto = popHelp.makePaymentDTO(type, currency);

        mockMvc.perform(post("/api/v1/payment")
                        .content(toJson(paymentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

        assertEquals(sizeBeforeInsert + 1, paymentsDao.findAll().size());
        assertEquals( 3, typeDao.findAll().size());
        assertEquals( 2, currencyDao.findAll().size());
        notificationsFacade.shutdown();
    }

    @Test
    void NotificationsSendOnlyWithCorrectType() throws Exception {

        // after each test clean up is not performed to see if any constraints valiotion would be triggered
        var notificationsCount = notificationDao.findAll().size();

        makeCallWithTypeCurreny(Type.TYPE_1, EUR);
        makeCallWithTypeCurreny(Type.TYPE_2, USD);
        makeCallWithTypeCurreny(Type.TYPE_3, USD);

        // verification
        notificationsFacade.shutdown();

        var notificationListOfTypes = notificationDao.findAll().stream().map(NotificationAttemptEntity::getType).toList();
        assertEquals(notificationsCount + 2, notificationListOfTypes.size());

        var bothTypesArePersisted = Stream.of(Type.TYPE_1, Type.TYPE_2)
                .map(Type::name)
                .anyMatch(notificationListOfTypes::contains);
        assertTrue(bothTypesArePersisted);
    }

    private void makeCallWithTypeCurreny(Type type, Currency currency) throws Exception {
        var paymentDto = popHelp.makePaymentDTO(type, currency);

        mockMvc.perform(post("/api/v1/payment")
                        .content(toJson(paymentDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    private String toJson(MakePaymentDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
