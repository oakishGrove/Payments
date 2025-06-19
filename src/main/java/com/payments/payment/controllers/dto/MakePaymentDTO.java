package com.payments.payment.controllers.dto;

import com.payments.payment.services.domain.entities.Currency;
import com.payments.payment.services.domain.entities.Type;

import java.math.BigDecimal;
import java.util.Objects;

public class MakePaymentDTO {

    private long id;

    private BigDecimal amount;

    private String debtorIban;

    private String creditorIban;

    private String details;

    private Currency currency;

    private Type type;

    private String bic;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
        return id == that.id
        && amount.equals(that.amount)
                && debtorIban.equals(that.debtorIban)
                && creditorIban.equals(that.creditorIban)
                && Objects.equals(details, that.details)
                && Objects.equals(bic, that.bic)
                && currency.equals(that.currency)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + amount.hashCode();
        result = 31 * result + debtorIban.hashCode();
        result = 31 * result + creditorIban.hashCode();
        result = 31 * result + Objects.hashCode(details);
        result = 31 * result + Objects.hashCode(bic);
        result = 31 * result + currency.hashCode();
        result = 31 * result + Objects.hashCode(type);
        return result;
    }

//    @Override
//    public String toString() {
//        return "{" +
//                id != null ? ("\"id\":" + id) : "" +
//                amount != null ? (", \"amount\":" + amount) : "" +
//                debtorIban != null ? (", \"debtorIban\":\"" + debtorIban + '"') : "" +
//                creditorIban != null ? (", \"creditorIban\":\"" + creditorIban + '"') : "" +
//                details != null ? (", \"details\":\"" + details + '"') : "" +
//                currency != null ? (", \"currency\":\"" + currency + '"') : "" +
//                type != null ? (", \"type\":\"" + type + '"') : "" +
//                bic != null ? ", \"bic\":\"" + bic + '"' : "" +
//                '}';
//    }
}
