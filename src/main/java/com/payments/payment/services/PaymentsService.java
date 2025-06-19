package com.payments.payment.services;

import com.payments.notifications.NotificationsFacade;
import com.payments.payment.aop.exceptions.controllers.PaymentCreationException;
import com.payments.payment.controllers.dto.MakePaymentDTO;
import com.payments.payment.repo.dao.PaymentsDao;
import com.payments.payment.repo.entities.CurrencyEntity;
import com.payments.payment.repo.entities.PaymentEntity;
import com.payments.payment.repo.entities.TypeEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.Random;


import static com.payments.payment.aop.StringUtils.nullOrEmpty;

@Service
public class PaymentsService {

    private PaymentsRepoMapper paymentsRepoMapper;
    private PaymentsDao paymentsDao;
    private NotificationsFacade notificationsFacade;

    private static Random random = new Random(Instant.now().getEpochSecond());

    public PaymentsService(PaymentsRepoMapper paymentsRepoMapper, PaymentsDao paymentsDao, NotificationsFacade notificationsFacade) {
        this.paymentsRepoMapper = paymentsRepoMapper;
        this.paymentsDao = paymentsDao;
        this.notificationsFacade = notificationsFacade;
    }

    @Transactional
    public void makePayment(MakePaymentDTO paymentDTO) {

        commonValidationOfPayment(paymentDTO);

        var payment = paymentsRepoMapper.transformToEntity(paymentDTO);

        postTransformationValidations(payment);

        payment.setCreated(new Date(Instant.now().toEpochMilli()));
        paymentsDao.save(payment);

        sendNotification(paymentDTO);
    }

    private void sendNotification(MakePaymentDTO payment) {

        switch (payment.getType()) {
            case TYPE_1 -> {

                var url = getUrl();
                notificationsFacade.sendNotification(url, payment.getType().name());
            }
            case TYPE_2 -> {

//                var url = getUrl();
                var url = "http://reddit.com:80";
                notificationsFacade.sendNotification(url, payment.getType().name());
            }
            default -> {}
        }
    }

    private String getUrl() {

//        return "https://httpstat.us/" + (random.nextBoolean() ? 200 : 507);
//        return "http://httpforever.com:80";
        return "http://google.com:80";
//        return "http://httpstat.us/201";
    }

    private void commonValidationOfPayment(MakePaymentDTO paymentDTO) {

        if (paymentDTO == null) {

            throw new PaymentCreationException("Empty body");
        }

        if (paymentDTO.getAmount() == null || paymentDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentCreationException("Not positive amount provided");
        }
        if (paymentDTO.getCurrency() == null) {
            throw new PaymentCreationException("Currency field required");
        }
        if (paymentDTO.getType() == null) {
            throw new PaymentCreationException("Payment type field is required");
        }
        if (nullOrEmpty(paymentDTO.getCreditorIban())) {
            throw new PaymentCreationException("Creditor IBAN field required");
        }

        validIbanFormat(paymentDTO.getCreditorIban());

        if (nullOrEmpty(paymentDTO.getDebtorIban())) {
            throw new PaymentCreationException("Debtor IBAN field required");
        }
        validIbanFormat(paymentDTO.getCreditorIban());

        switch (paymentDTO.getType()) {
            case TYPE_1:

                if (nullOrEmpty(paymentDTO.getDetails())) {
                    throw new PaymentCreationException("Details field required for TYPE_1");
                }
                if (paymentDTO.getBic() != null) {
                    throw new PaymentCreationException("BIC field not supported for TYPE_1");
                }
                break;
            case TYPE_2:

                if (paymentDTO.getBic() != null) {
                    throw new PaymentCreationException("BIC field not supported for TYPE_2");
                }
                break;
            case TYPE_3:

                if (nullOrEmpty(paymentDTO.getBic())) {
                    throw new PaymentCreationException("BIC field required for TYPE_3");
                }

                validBicCode(paymentDTO.getBic());

                if (paymentDTO.getDetails() != null) {
                    throw new PaymentCreationException("Details field not supported for TYPE_3");
                }
                break;
        }
    }

    private void postTransformationValidations(PaymentEntity payment) {

        if (!isCurrencyValidWithType(payment.getCurrency(), payment.getType())) {
            throw new PaymentCreationException("Specified type %s doesn't support this currency %s"
                    .formatted(payment.getType().getName(), payment.getCurrency().getName()));
        }
    }

    private boolean isCurrencyValidWithType(CurrencyEntity expectedCurrency, TypeEntity type) {

        if (expectedCurrency == null || type == null) {
            return false;
        }

        return type.getCurrencyList().stream()
                .map(CurrencyEntity::getName)
                .anyMatch(currencyName -> currencyName.equals(expectedCurrency.getName()));
    }

    private void validIbanFormat(String creditorIban) {
    }

    private void validBicCode(String bic) {
    }
}
