package com.factotum.oaka.service;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@IntegrationTest
class TransactionRetrievalServiceImplIT {

    @Autowired
    private TransactionService transactionService;

    @Test
    void getAllTransactions_ReturnsTestTransactions() {

        List<Transaction> transactions = transactionService.getAllTransactions();
        assertThat(transactions, is(not(nullValue())));
        assertThat(transactions, hasSize(891));

    }

    @Test
    void getAllTransactionDtos_ReturnsTestTransactionsAsDtos() {

        Set<TransactionDto> transactionDtos = transactionService.getAllTransactionDtos();
        assertThat(transactionDtos, is(not(nullValue())));
        assertThat(transactionDtos, hasSize(891));

    }
}
