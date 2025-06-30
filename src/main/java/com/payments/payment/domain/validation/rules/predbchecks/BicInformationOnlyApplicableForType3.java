package com.payments.payment.domain.validation.rules.predbchecks;

import com.payments.payment.domain.validation.MakePaymentPreDb;
import com.payments.common.validation.validationrules.ValidateBic;
import com.payments.payment.dto.MakePaymentDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.payments.payment.domain.entities.Type.TYPE_3;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Component
public class BicInformationOnlyApplicableForType3 implements MakePaymentPreDb {

    private final ValidateBic validateBic;

    public BicInformationOnlyApplicableForType3(ValidateBic validateBic) {
        this.validateBic = validateBic;
    }

    /**
     * Preconditions @Class PaymentEntity object and type field not null
     * @param makePaymentDTO
     * @return
     */
    @Override
    public Optional<String> validate(MakePaymentDTO makePaymentDTO) {

        var paymentType = makePaymentDTO.getType();

        if (TYPE_3.equals(paymentType)) {

            if (!validateBic.valid(makePaymentDTO.getBic())) {

                return of("BIC field required for TYPE_3");
            }
        } else {

            if (makePaymentDTO.getBic() != null) {

                return of("BIC field not supported for " + paymentType.name());
            }
        }

        return empty();
    }
}
