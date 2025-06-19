package com.payments.payment.repo.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;


@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false, precision = 20, scale = 10)
    private BigDecimal amount;

    @Column(name = "debtorIban", nullable = false, length = 34)
    private String debtorIban;

    @Column(name = "creditorIban", nullable = false, length = 34)
    private String creditorIban;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "details")
    private String details;

    @Column(name = "IBAN", length = 8)
    private String BIC;

    @JoinColumn(name = "currency_id", nullable = false)
    @ManyToOne
    private CurrencyEntity currency;


    @JoinColumn(name = "type_id", nullable = false)
    @ManyToOne(optional = false)
    private TypeEntity type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBic(String bic) {
        this.BIC = bic;
    }

    @Override
    public String toString() {
        return "PaymentEntity{" +
                "id=" + id +
                ", amount=" + amount +
                ", debtorIban='" + debtorIban + '\'' +
                ", creditorIban='" + creditorIban + '\'' +
                ", created=" + created +
                ", details='" + details + '\'' +
                ", BIC='" + BIC + '\'' +
                ", currency=" + currency +
                ", type=" + type +
                '}';
    }
}
