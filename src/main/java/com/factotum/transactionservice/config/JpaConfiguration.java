package com.factotum.transactionservice.config;

import com.factotum.transactionservice.converter.BudgetSummaryConverter;
import com.factotum.transactionservice.converter.TransactionCategoryConverter;
import com.factotum.transactionservice.converter.TransactionDtoConverter;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.util.List;

@Configuration
@EnableR2dbcRepositories(basePackages = {"com.factotum.transactionservice.repository"})
public class JpaConfiguration extends AbstractR2dbcConfiguration {

    @Autowired
    @Qualifier("connectionFactory")
    private ConnectionFactory connectionFactory;

    @Bean
    @Profile({"test"})
    public ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("test_data/drop_tables.sql"),
                new ClassPathResource("db/migration/V1_0__create_mm_transaction_schema.sql"),
                new ClassPathResource("db/migration/V2_0__add_tenant_identifier.sql"),
                new ClassPathResource("test_data/V1_1__add_test_data.sql"),
                new ClassPathResource("test_data/V2_1__add_tenant_id.sql")
        );
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Override
    @NonNull
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

    @Override
    @NonNull
    protected List<Object> getCustomConverters() {
        return List.of(new BudgetSummaryConverter(), new TransactionDtoConverter(), new TransactionCategoryConverter());
    }

}
