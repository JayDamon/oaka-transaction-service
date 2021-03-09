package com.factotum.oaka.repository;

import com.factotum.oaka.config.JpaConfiguration;
import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@DataR2dbcTest
@ActiveProfiles({"test"})
@Import(JpaConfiguration.class)
class TransactionRepositoryIT {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void getBudgetSummaries() {
        TransactionBudgetSummary summary = transactionRepository
                .getBudgetSummaries(
                        1,
                        2017,
                        new HashSet<>(Arrays.asList(10L, 11L, 12L, 13L, 14L, 15L, 26L, 16L, 17L, 18L, 27L, 19L, 28L, 29L, 30L)),
                        2
                )
                .block();
        assertThat(summary, is(not(nullValue())));

        System.out.println(summary);

        assertThat(summary.getActual(), is(greaterThan(BigDecimal.ZERO)));

    }

    @Test
    void test() {
        Flux<Transaction> transactionList = transactionRepository.findAll();
        StepVerifier.create(transactionList.log()).expectNextCount(891).verifyComplete();
//        assertThat(transactionList, hasSize(greaterThan(0)));
    }

}
