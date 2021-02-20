package com.factotum.oaka.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles({"test"})
class TransactionRepositoryIT {

    @Autowired
    private TransactionRepository transactionRepository;

//    @Test
//    void getBudgetSummaries() {
//        TransactionBudgetSummary summary = transactionRepository.getBudgetSummaries(2017, 1, 1, 1).get();
//
//        assertThat(summary.getActual(), is(greaterThan(BigDecimal.ZERO)));
//
//        System.out.println(summary);
//    }

}
