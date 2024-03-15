package com.factotum.transactionservice.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

public class ConnectionFactoryBase {

    @Configuration
    public static class TestDatabaseConfig {

        @Bean
        public ConnectionFactory connectionFactory(DataSource dataSource) {
            return new EmbeddedPostgresConnectionFactory(dataSource);
        }
    }

}
