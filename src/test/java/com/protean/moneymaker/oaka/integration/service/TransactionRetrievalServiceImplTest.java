package com.protean.moneymaker.oaka.integration.service;

import com.protean.moneymaker.oaka.integration.IntegrationTest;
import com.protean.moneymaker.oaka.service.TransactionRetrievalService;
import com.protean.moneymaker.oaka.service.TransactionRetrievalServiceImpl;
import com.protean.moneymaker.rin.dto.TransactionDto;
import com.protean.moneymaker.rin.model.Transaction;
import com.protean.moneymaker.rin.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@IntegrationTest
class TransactionRetrievalServiceImplTest {

    @Autowired private TransactionService transactionService;

    private TransactionRetrievalService transactionRetrievalService;

    @BeforeEach
    void setUp() {
        transactionRetrievalService = new TransactionRetrievalServiceImpl(transactionService);
    }

    @Test
    void getAllTransactions_ReturnsTestTransactions() {

        List<Transaction> transactions = transactionRetrievalService.getAllTransactions();
        assertThat(transactions, is(not(nullValue())));
        assertThat(transactions, hasSize(891));

    }

    @Test
    void getAllTransactionDtos_ReturnsTestTransactionsAsDtos() {

        Set<TransactionDto> transactionDtos = transactionRetrievalService.getAllTransactionDtos();
        assertThat(transactionDtos, is(not(nullValue())));
        assertThat(transactionDtos, hasSize(891));

    }
}
