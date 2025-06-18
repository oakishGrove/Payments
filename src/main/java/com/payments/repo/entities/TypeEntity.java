package com.payments.repo.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "type")
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(precision = 12, scale = 10)
    private BigDecimal cancellationCoefficient;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "type_currency",
            joinColumns = {@JoinColumn(name = "type_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "currency_id", nullable = false)}
    )
    private List<CurrencyEntity> currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCancellationCoefficient() {
        return cancellationCoefficient;
    }

    public void setCancellationCoefficient(BigDecimal cancellationCoefficient) {
        this.cancellationCoefficient = cancellationCoefficient;
    }

    public List<CurrencyEntity> getCurrencyList() {
        return currency;
    }

    public void setCurrency(List<CurrencyEntity> currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        TypeEntity that = (TypeEntity) o;
        return Objects.equals(id, that.id)
                    && Objects.equals(name, that.name)
                    && Objects.equals(cancellationCoefficient, that.cancellationCoefficient)
                    && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(cancellationCoefficient);
        result = 31 * result + Objects.hashCode(currency);
        return result;
    }

    @Override
    public String toString() {
        return "TypeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cancellationCoefficient=" + cancellationCoefficient +
                ", currency=" + currency +
                '}';
    }
}
