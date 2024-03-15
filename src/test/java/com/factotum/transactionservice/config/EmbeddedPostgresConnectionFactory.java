package com.factotum.transactionservice.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.api.PostgresqlConnection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.postgresql.ds.common.BaseDataSource;
import org.reactivestreams.Publisher;

import javax.sql.DataSource;
import java.sql.SQLException;

public class EmbeddedPostgresConnectionFactory implements ConnectionFactory {

    private final DataSource dataSource; // an AOP proxy with a changing target database

    private volatile BaseDataSource config; // the last instance of the target database
    private volatile PostgresqlConnectionFactory factory;

    public EmbeddedPostgresConnectionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private PostgresqlConnectionFactory connectionFactory() {
        BaseDataSource freshConfig;
        try {
            freshConfig = dataSource.unwrap(BaseDataSource.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (factory == null || config != freshConfig) { // checks if the target database has changed or not
            config = freshConfig;
            factory = new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                    .host(freshConfig.getServerName())
                    .port(freshConfig.getPortNumber())
                    .username(freshConfig.getUser())
                    .password(freshConfig.getPassword())
                    .database(freshConfig.getDatabaseName())
                    .build());
        }

        return factory;
    }

    @Override
    public Publisher<PostgresqlConnection> create() {
        return connectionFactory().create();
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        return connectionFactory().getMetadata();
    }
}
