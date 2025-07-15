package com.payments.payment.services;

import com.payments.common.validation.ValidationEngine;
import com.payments.common.exceptions.PaymentCreationException;
import com.payments.payment.dto.MakePaymentDTO;
import com.payments.payment.repo.CurrencyEntityRepo;
import com.payments.payment.repo.TypeEntityRepo;
import com.payments.payment.repo.entities.PaymentEntity;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class PaymentsService {

    private final PaymentsRepoMapper paymentsRepoMapper;
    private final CurrencyEntityRepo currencyEntityRepo;
    private final TypeEntityRepo typeEntityRepo;
    private final PaymentsEntityService paymentsEntityService;

    private final ValidationEngine<MakePaymentDTO> makePaymentPreDBValidator;
    private final ValidationEngine<PaymentEntity> makePaymentPostDBValidator;

    private final NotificationsService notificationsService;

    public PaymentsService(PaymentsRepoMapper paymentsRepoMapper
            , CurrencyEntityRepo currencyEntityRepo
            , TypeEntityRepo typeEntityRepo
            , PaymentsEntityService paymentsEntityService
            , ValidationEngine<MakePaymentDTO> makePaymentPreDBValidator
            , ValidationEngine<PaymentEntity> makePaymentPostDBValidator
            , NotificationsService notificationsService
    ) {
        this.paymentsRepoMapper = paymentsRepoMapper;
        this.currencyEntityRepo = currencyEntityRepo;
        this.typeEntityRepo = typeEntityRepo;
        this.paymentsEntityService = paymentsEntityService;
        this.makePaymentPreDBValidator = makePaymentPreDBValidator;
        this.makePaymentPostDBValidator = makePaymentPostDBValidator;
        this.notificationsService = notificationsService;
    }

    @Transactional
    public void makePayment(@Valid MakePaymentDTO paymentDTO) {

        makePaymentPreDBValidator
                .validate(paymentDTO)
                .ifPresent(errors -> {
                    throw new PaymentCreationException(errors);
                });

        // todo: seal from there
        var currency = currencyEntityRepo.getCurrencyByName(paymentDTO.getCurrency().name(),
                () -> new PaymentCreationException("This currency doesn't exist"));

        var type = typeEntityRepo.getTypeByName(paymentDTO.getType().name(),
                () -> new PaymentCreationException("This type doesn't exist"));

        var payment = paymentsRepoMapper.transformToEntity(paymentDTO, type, currency);

        makePaymentPostDBValidator
                .validate(payment)
                .ifPresent(errors -> {
                    throw new PaymentCreationException(errors);
                });

        paymentsEntityService.savePayment(payment);
        // till here

        notificationsService.sendNotification(paymentDTO);
    }
}
