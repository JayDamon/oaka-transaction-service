package com.factotum.oaka.service;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@IntegrationTest
class TransactionRetrievalServiceImplIT {

    @Autowired
    private TransactionService transactionService;

    @Test
    void getAllTransactions_ReturnsTestTransactions() {

        Flux<Transaction> transactions = transactionService.getAllTransactions();
        StepVerifier.create(transactions.log()).expectNextCount(891).verifyComplete();

    }

    @Test
    void getAllTransactionDtos_ReturnsTestTransactionsAsDtos() {

        Flux<TransactionDto> transactionDtos = transactionService.getAllTransactionDtos();
        StepVerifier.create(transactionDtos.log()).expectNextCount(891).verifyComplete();
//        assertThat(transactionDtos, is(not(nullValue())));
//        assertThat(transactionDtos, hasSize(891));

    }
}
