package com.factotum.oaka.repository;

import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@DataJpaTest
@ActiveProfiles({"test"})
class TransactionRepositoryIT {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void getBudgetSummaries() {
        TransactionBudgetSummary summary = transactionRepository
                .getBudgetSummaries(
                        2017,
                        1
                        ,
                        new HashSet<>(Arrays.asList(10L, 11L, 12L, 13L, 14L, 15L, 26L, 16L, 17L, 18L, 27L, 19L, 28L, 29L, 30L)),
                        2
                )
                .orElse(null);
        assertThat(summary, is(not(nullValue())));

        System.out.println(summary);

        assertThat(summary.getActual(), is(greaterThan(BigDecimal.ZERO)));

    }

    @Test
    void test() {
        List<Transaction> transactionList = transactionRepository.findAll();
        System.out.println(transactionList);
        assertThat(transactionList, hasSize(greaterThan(0)));
    }

}
