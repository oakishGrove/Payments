package com.payments.repo.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "currency")
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "currency",fetch = FetchType.LAZY)
    private List<TypeEntity> typeEntity;

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

    public List<TypeEntity> getTypeEntity() {
        return typeEntity;
    }

    public void setTypeEntity(List<TypeEntity> typeEntity) {
        this.typeEntity = typeEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyEntity that = (CurrencyEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(typeEntity, that.typeEntity);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(typeEntity);
        return result;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
