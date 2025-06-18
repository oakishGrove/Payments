package com.payments.services;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@TestConfiguration(proxyBeanMethods = false)
public class IntegrationTestConfig {

    @Bean
    public PostgreSQLContainer postgreSQLContainer() {
        var container = new PostgreSQLContainer("postgres:17.1");
        container.start();

        return container;
    }

    @Bean
    @Primary
    public DataSource dataSource(PostgreSQLContainer container) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        return dataSource;
    }
}
