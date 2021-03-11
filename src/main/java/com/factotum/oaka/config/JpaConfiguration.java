package com.factotum.oaka.config;

import com.factotum.oaka.converter.BudgetSummaryConverter;
import com.factotum.oaka.converter.TransactionCategoryConverter;
import com.factotum.oaka.converter.TransactionDtoConverter;
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
@EnableR2dbcRepositories(basePackages = {"com.factotum.oaka.repository"})
public class JpaConfiguration extends AbstractR2dbcConfiguration {

    @Autowired
    @Qualifier("connectionFactory")
    private ConnectionFactory connectionFactory;

    @Bean
    @Profile({"test", "h2"})
    public ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql"),
                new ClassPathResource("data.sql")
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
