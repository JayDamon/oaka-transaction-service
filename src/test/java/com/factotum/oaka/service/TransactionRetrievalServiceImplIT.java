package com.factotum.oaka.service;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.BudgetCategoryDto;
import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@IntegrationTest
class TransactionRetrievalServiceImplIT {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private BudgetService budgetService;

    @BeforeEach
    void setup() {
        when(accountService.getAccountById(anyLong())).thenAnswer(i ->
                Mono.just(new ShortAccountDto(i.getArgument(0), "AccountName")));
        when(budgetService.getBudgetById(anyLong())).thenAnswer(i -> {

            BudgetDto budget = new BudgetDto();
            budget.setId(i.getArgument(0));
            budget.setName("TestName");
            BudgetCategoryDto budgetCategoryDto = new BudgetCategoryDto();
            budgetCategoryDto.setId(1);
            budgetCategoryDto.setName("BudgetCategoryName");
            budgetCategoryDto.setTypeName("Type");
            budget.setBudgetCategory(budgetCategoryDto);
            return Mono.just(budget);
        });
    }

    @Test
    void getAllTransactions_ReturnsTestTransactions() {

        Flux<Transaction> transactions = transactionService.getAllTransactions();
        StepVerifier.create(transactions.log()).expectNextCount(891).verifyComplete();

    }

    @Test
    void getAllTransactionDtos_ReturnsTestTransactionsAsDtos() {

        Flux<TransactionDto> transactionDtos = transactionService.getAllTransactionDtos();
        StepVerifier.create(transactionDtos.log()).expectNextCount(891).verifyComplete();

    }
}
