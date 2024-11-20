package com.factotum.transactionservice.repository;

//import com.factotum.oaka.config.JpaConfiguration;

import com.factotum.transactionservice.config.ConnectionFactoryBase;
import com.factotum.transactionservice.config.JpaConfiguration;
import com.factotum.transactionservice.dto.TransactionBudgetSummary;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataR2dbcTest
@AutoConfigureEmbeddedDatabase
@ActiveProfiles({"test"})
@Import({JpaConfiguration.class, ConnectionFactoryBase.TestDatabaseConfig.class})
class TransactionRepositoryIntegrationTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void getExpenseTransactionSummary_GivenExpenseTransactionsExistInRange_ThenReturnValue() {
        TransactionBudgetSummary summary = transactionRepository
                .getExpenseTransactionSummary(
                        1,
                        2017,
                        new HashSet<>(List.of(
                                UUID.fromString("b115146c-a9c2-4f2f-806f-2e9ea4a429a6"),
                                UUID.fromString("739f6b39-1e2e-4818-98b4-223abe0cf332"),
                                UUID.fromString("f89a3a6e-e422-4f19-af70-22a650bec2bb"),
                                UUID.fromString("d3197c02-552a-461e-b1e1-5007295ec4d6"),
                                UUID.fromString("31e92edd-8803-4762-b4e5-f523fc09deef"),
                                UUID.fromString("5d96fecb-a47b-4dbd-b4e9-c48662607578"),
                                UUID.fromString("9322efc2-39e7-4e71-bd6b-bfdd933ddd75"),
                                UUID.fromString("9f2fd26e-593a-4704-a501-eaf001e52c7c"),
                                UUID.fromString("87e7c121-0fc4-468b-9470-b361fad5d7a6"),
                                UUID.fromString("cc13b3f9-0d59-49ee-9024-904b9f385a08"),
                                UUID.fromString("b72b9a68-30f7-455f-8222-700b3ac39d35"),
                                UUID.fromString("eef4b453-84ed-46f9-95a3-1a324acca97c"),
                                UUID.fromString("2d9acab8-7412-4232-a1db-79ae83765cce"),
                                UUID.fromString("2d9acab8-7412-4232-a1db-79ae83765cce"),
                                UUID.fromString("5de78a99-c5b6-41c0-a643-c8d9e6cdbb57"))),
                        "5809b48e-b705-4b3e-be9f-16ce0380cb45"
                )
                .block();
        assertThat(summary, is(not(nullValue())));

        assertThat(summary.getActual(), is(equalTo(BigDecimal.valueOf(380.51))));
        assertThat(summary.getMonth(), is(equalTo(1)));
        assertThat(summary.getYear(), is(equalTo(2017)));

    }

    @Test
    void getIncomeTransactionSummary_GivenIncomeTransactionsExistInRange_ThenReturnValue() {
        TransactionBudgetSummary summary = transactionRepository
                .getIncomeTransactionSummary(
                        1,
                        2017,
                        new HashSet<>(List.of(UUID.fromString("33c31199-202a-4443-8f09-417ea8acd7d3"))),
                        "5809b48e-b705-4b3e-be9f-16ce0380cb45")
                .block();
        assertThat(summary, is(not(nullValue())));

        assertThat(summary.getActual(), is(equalTo(BigDecimal.valueOf(2000))));
        assertThat(summary.getMonth(), is(equalTo(1)));
        assertThat(summary.getYear(), is(equalTo(2017)));

    }

}
