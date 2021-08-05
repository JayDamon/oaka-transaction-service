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
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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
        List<ShortAccountDto> shortAccountDtos = LongStream.range(0, 40).mapToObj(i -> new ShortAccountDto(i, "AccountName)")).collect(Collectors.toList());
        when(accountService.getAccounts()).thenReturn(Flux.fromIterable(shortAccountDtos));

        List<BudgetDto> budgetDtos = LongStream.range(0, 40).mapToObj(l -> {
            BudgetDto budget = new BudgetDto();
            budget.setId(l);
            budget.setName("TestName");
            budget.setFrequencyTypeName("FrequencyType");
            budget.setAmount(BigDecimal.valueOf(22.45));
            budget.setInUse(false);
            BudgetCategoryDto budgetCategoryDto = new BudgetCategoryDto();
            budgetCategoryDto.setId(1);
            budgetCategoryDto.setName("BudgetCategoryName");
            budgetCategoryDto.setTypeName("Type");
            budget.setBudgetCategory(budgetCategoryDto);
            return budget;
        }).collect(Collectors.toList());
        when(budgetService.getBudgets()).thenReturn(Flux.fromIterable(budgetDtos));
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
