package com.payments.payment.dto;

import com.payments.common.validation.NotNullOrEmpty;
import com.payments.common.validation.PositiveBigDecimal;
import com.payments.payment.domain.entities.Currency;
import com.payments.payment.domain.entities.Type;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public class MakePaymentDTO {

        @PositiveBigDecimal(message = "Not positive amount provided")
        private BigDecimal amount;

        @NotNullOrEmpty(message = "Debtor IBAN field required")
        private String debtorIban;

        @NotNullOrEmpty(message = "Creditor IBAN field required")
        private String creditorIban;

        @NotNull(message = "Currency field required")
        private Currency currency;

        @NotNull(message = "Payment type field is required")
        private Type type;

        String details;
        String bic;

        public BigDecimal getAmount() {
                return amount;
        }

        public void setAmount(BigDecimal amount) {
                this.amount = amount;
        }

        public String getDebtorIban() {
                return debtorIban;
        }

        public void setDebtorIban(String debtorIban) {
                this.debtorIban = debtorIban;
        }

        public String getCreditorIban() {
                return creditorIban;
        }

        public void setCreditorIban(String creditorIban) {
                this.creditorIban = creditorIban;
        }

        public Currency getCurrency() {
                return currency;
        }

        public void setCurrency(Currency currency) {
                this.currency = currency;
        }

        public String getDetails() {
                return details;
        }

        public void setDetails(String details) {
                this.details = details;
        }

        public Type getType() {
                return type;
        }

        public void setType(Type type) {
                this.type = type;
        }

        public String getBic() {
                return bic;
        }

        public void setBic(String bic) {
                this.bic = bic;
        }

        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;

                MakePaymentDTO that = (MakePaymentDTO) o;
                return Objects.equals(amount, that.amount) && Objects.equals(debtorIban, that.debtorIban) && Objects.equals(creditorIban, that.creditorIban) && currency == that.currency && type == that.type && Objects.equals(details, that.details) && Objects.equals(bic, that.bic);
        }

        @Override
        public int hashCode() {
                int result = Objects.hashCode(amount);
                result = 31 * result + Objects.hashCode(debtorIban);
                result = 31 * result + Objects.hashCode(creditorIban);
                result = 31 * result + Objects.hashCode(currency);
                result = 31 * result + Objects.hashCode(type);
                result = 31 * result + Objects.hashCode(details);
                result = 31 * result + Objects.hashCode(bic);
                return result;
        }
}
