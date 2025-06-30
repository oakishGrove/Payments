package com.payments.payment.services;

import com.payments.common.exceptions.PaymentCreationException;
import com.payments.payment.domain.entities.Type;
import com.payments.payment.dto.MakePaymentDTO;
import com.payments.payment.repo.dao.CurrencyDao;
import com.payments.payment.repo.dao.PaymentsDao;
import com.payments.payment.repo.dao.TypeDao;
import com.payments.payment.repo.entities.CurrencyEntity;
import com.payments.payment.repo.entities.TypeEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.payments.payment.domain.entities.Currency.EUR;
import static com.payments.payment.domain.entities.Currency.USD;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Import(IntegrationTestConfig.class)
class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentsService paymentsService;
    @Autowired
    private TypeDao typeDao;
    @Autowired
    private CurrencyDao currencyDao;
    @Autowired
    private PaymentsDao paymentsDao;
    @Autowired
    private JdbcTemplate template;

    @AfterEach
    void clearTables() {
        paymentsDao.deleteAll();
        typeDao.deleteAll();
        currencyDao.deleteAll();
    }

    @Test
    void specifiedCurrencySupportedByType() {

        MakePaymentDTO dto = createMakePaymentDTO();
        Type TYPE = Type.TYPE_1;
        dto.setType(TYPE);
        dto.setCurrency(EUR);
        dto.setDetails("Details1");

        var persistedCurrency1 = persistCurrency(USD.name());
        var persistedCurrency2 = persistCurrency(EUR.name());

        persistType(TYPE.name(), List.of(persistedCurrency1, persistedCurrency2));

        assertDoesNotThrow(() -> paymentsService.makePayment(dto));
    }


    @Test
    void throwExceptionWhenSpecifiedCurrencyNotPersisted() {

        MakePaymentDTO dto = createMakePaymentDTO();
        Type TYPE = Type.TYPE_1;
        dto.setType(TYPE);
        dto.setCurrency(EUR);
        dto.setDetails("Details1");

        persistType(TYPE.name(), null);

        var ex = assertThrows(PaymentCreationException.class,
                () -> paymentsService.makePayment(dto));
        assertEquals("This currency doesn't exist", ex.getMessage());
    }

    @Test
    void specifiedCurrencyIsNotSupportedByType() {

        MakePaymentDTO dto = createMakePaymentDTO();
        Type TYPE = Type.TYPE_1;
        dto.setType(TYPE);
        dto.setCurrency(USD);
        dto.setDetails("Details1");

        var persistedCurrency1 = persistCurrency(EUR.name());
        persistCurrency(USD.name());

        persistType(TYPE.name(), List.of(persistedCurrency1));

        var ex = assertThrows(PaymentCreationException.class,
                () -> paymentsService.makePayment(dto));
        paymentCreationExceptionContainsReason(ex, "Specified type TYPE_1 doesn't support this currency USD");
    }

    private TypeEntity persistType(String name, List<CurrencyEntity> currencyList) {

        TypeEntity typeEntity = new TypeEntity();
        typeEntity.setName(name);
        typeEntity.setCurrency(currencyList);
        return typeDao.save(typeEntity);
    }

    private CurrencyEntity persistCurrency(String name) {
        CurrencyEntity currency = new CurrencyEntity();
        currency.setName(name);
        return currencyDao.save(currency);
    }

    @Test
    void throwExceptionWithNegativeAmount() {

        MakePaymentDTO dto = createMakePaymentDTO();
        dto.setAmount(BigDecimal.valueOf(-.999));

        var ex = assertThrows(ConstraintViolationException.class,
                () -> paymentsService.makePayment(dto));
        constraintExceptionContainsErrorMessage(ex, "Not positive amount provided");
    }

    @Test
    void throwExceptionWhenCurrencyIsNull() {

        MakePaymentDTO dto = createMakePaymentDTO();
        dto.setCurrency(null);

        var ex = assertThrows(ConstraintViolationException.class,
                () -> paymentsService.makePayment(dto));
        constraintExceptionContainsErrorMessage(ex, "Currency field required");
    }


    @Test
    void throwExceptionWhenTypeIsNull() {

        MakePaymentDTO dto = createMakePaymentDTO();
        dto.setType(null);

        var ex = assertThrows(ConstraintViolationException.class,
                () -> paymentsService.makePayment(dto));

        constraintExceptionContainsErrorMessage(ex, "Payment type field is required");
    }

    @ParameterizedTest
    @MethodSource("emptyStringStream")
    void throwExceptionWhenCreditorIbanNotProvided(String debtorIban) {

        MakePaymentDTO dto = createMakePaymentDTO();
        dto.setDebtorIban(debtorIban);

        var ex = assertThrows(ConstraintViolationException.class,
                () -> paymentsService.makePayment(dto));
        constraintExceptionContainsErrorMessage(ex, "Debtor IBAN field required");
    }

    @ParameterizedTest
    @MethodSource("emptyStringStream")
    void throwExceptionWhenDetailsNotProvidedWithType1(String details) {
        MakePaymentDTO dto = createMakePaymentDTO();
        dto.setType(Type.TYPE_1);
        dto.setDetails(details);

        var ex = assertThrows(PaymentCreationException.class,
                () -> paymentsService.makePayment(dto));

        paymentCreationExceptionContainsReason(ex, "Details field required for TYPE_1");
    }

    @ParameterizedTest
    @EnumSource(value=Type.class, names = {"TYPE_1", "TYPE_2"})
    void throwExceptionWhenBicProvidedWithTypes(Type type) {
        MakePaymentDTO dto = createMakePaymentDTO();
        dto.setType(type);
        dto.setDetails("d");
        dto.setBic("");

        var ex = assertThrows(PaymentCreationException.class,
                () -> paymentsService.makePayment(dto));
        paymentCreationExceptionContainsReason(ex, "BIC field not supported for " + type.name());
    }

    @Test
    void throwExceptionWhenBicProvidedWithType2() {
        MakePaymentDTO dto = createMakePaymentDTO();
        dto.setType(Type.TYPE_1);
        dto.setDetails("d");
        dto.setBic("");

        var ex = assertThrows(PaymentCreationException.class,
                () -> paymentsService.makePayment(dto));
        paymentCreationExceptionContainsReason(ex, "BIC field not supported for TYPE_1");
    }


    @ParameterizedTest
    @MethodSource("emptyStringStream")
    void throwExceptionWhenBicNotProvided(String emptyString) {
        MakePaymentDTO dto = createMakePaymentDTO();
        dto.setType(Type.TYPE_3);
        dto.setBic(emptyString);

        var ex = assertThrows(PaymentCreationException.class,
                () -> paymentsService.makePayment(dto));
        paymentCreationExceptionContainsReason(ex, "BIC field required for TYPE_3");
    }

    static List<String> emptyStringStream() {
        return Arrays.asList("", null, " ", "\n");
    }

    private MakePaymentDTO createMakePaymentDTO() {
        MakePaymentDTO dto = new MakePaymentDTO();
        dto.setAmount(BigDecimal.valueOf(0.0001));
        dto.setCurrency(EUR);
        dto.setCreditorIban("creditorIban");
        dto.setDebtorIban("debtorIban");
        dto.setType(Type.TYPE_1);
        dto.setDetails("Detail1");

        return dto;
    }

    private void constraintExceptionContainsErrorMessage(ConstraintViolationException ex, String errorMessage) {
        assertTrue(ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .anyMatch(msg -> msg.equals(errorMessage)));
    }

    private void paymentCreationExceptionContainsReason(PaymentCreationException ex, String expectedReason) {

        assertTrue(ex.getReasons().stream()
                .anyMatch(eR -> eR.equals(expectedReason)), "expected reason: " + expectedReason
                + " in " + ex.getReasons());
    }
}