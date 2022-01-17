package com.factotum.transactionservice.repository;

//import com.factotum.oaka.config.JpaConfiguration;

import com.factotum.transactionservice.config.JpaConfiguration;
import com.factotum.transactionservice.dto.TransactionBudgetSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataR2dbcTest
@ActiveProfiles({"test"})
@Import(JpaConfiguration.class)
class TransactionRepositoryIntegrationTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void getExpenseTransactionSummary_GivenExpenseTransactionsExistInRange_ThenReturnValue() {
        TransactionBudgetSummary summary = transactionRepository
                .getExpenseTransactionSummary(
                        1,
                        2017,
                        new HashSet<>(Arrays.asList(10L, 11L, 12L, 13L, 14L, 15L, 26L, 16L, 17L, 18L, 27L, 19L, 28L, 29L, 30L)),
                        "684996db-6cf8-4976-8336-6e664386dcda"
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
                        new HashSet<>(List.of(20L)),
                        "684996db-6cf8-4976-8336-6e664386dcda"
                )
                .block();
        assertThat(summary, is(not(nullValue())));

        assertThat(summary.getActual(), is(equalTo(BigDecimal.valueOf(2000))));
        assertThat(summary.getMonth(), is(equalTo(1)));
        assertThat(summary.getYear(), is(equalTo(2017)));

    }

}
