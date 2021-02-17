package com.protean.moneymaker.oaka.repository;

import com.protean.moneymaker.oaka.dto.TransactionBudgetSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

@DataJpaTest
@ActiveProfiles({"test"})
class TransactionRepositoryIntegrationTest {

    @Autowired private TransactionRepository transactionRepository;

//    @Test
//    void getBudgetSummaries() {
//        TransactionBudgetSummary summary = transactionRepository.getBudgetSummaries(2017, 1, 1, 1).get();
//
//        assertThat(summary.getActual(), is(greaterThan(BigDecimal.ZERO)));
//
//        System.out.println(summary);
//    }

}
