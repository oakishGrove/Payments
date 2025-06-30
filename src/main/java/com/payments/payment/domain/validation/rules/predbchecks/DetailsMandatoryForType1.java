package com.payments.payment.domain.validation.rules.predbchecks;

import com.payments.payment.domain.validation.MakePaymentPreDb;
import com.payments.common.validation.validationrules.ValidateNotNullOrEmpty;
import com.payments.payment.dto.MakePaymentDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.payments.payment.domain.entities.Type.TYPE_1;

@Component
public class DetailsMandatoryForType1 implements MakePaymentPreDb {

    private final ValidateNotNullOrEmpty validateNotNullOrEmpty;

    public DetailsMandatoryForType1(ValidateNotNullOrEmpty validateNotNullOrEmpty) {
        this.validateNotNullOrEmpty = validateNotNullOrEmpty;
    }

    @Override
    public Optional<String> validate(MakePaymentDTO paymentEntity) {

        if (!validateNotNullOrEmpty.validate(paymentEntity.getDetails())) {

            return Optional.of("Details field required for TYPE_1");
        }

        return Optional.empty();
    }

//    @Override
    public boolean applicable(MakePaymentDTO paymentEntity) {

        return TYPE_1.equals(paymentEntity.getType());
    }
}
