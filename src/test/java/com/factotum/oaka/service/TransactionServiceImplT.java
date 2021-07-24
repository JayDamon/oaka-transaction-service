package com.factotum.oaka.service;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.BudgetCategoryDto;
import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@IntegrationTest
class TransactionServiceImplT {

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
            budget.setFrequencyTypeName("FrequencyType");
            budget.setAmount(BigDecimal.valueOf(22.45));
            budget.setInUse(false);
            BudgetCategoryDto budgetCategoryDto = new BudgetCategoryDto();
            budgetCategoryDto.setId(1);
            budgetCategoryDto.setName("BudgetCategoryName");
            budgetCategoryDto.setTypeName("Type");
            budget.setBudgetCategory(budgetCategoryDto);
            return Mono.just(budget);
        });
    }

    @Test
    void getAllTransactionDtos() {

        Flux<TransactionDto> transactions = transactionService.getAllTransactionDtos();

        List<TransactionDto> dtos = transactions.collectList().block();

        assertThat(dtos, hasSize(891));

        TransactionDto dto = dtos.stream().sorted(Comparator.comparingLong(TransactionDto::getId)).collect(Collectors.toList()).get(0);

        if (dto.getId().equals(2L)) {
            assertThat(dto.getId(), is(not(nullValue())));
            assertThat(dto.getAmount(), is(not(nullValue())));
            assertThat(dto.getDescription(), is(not(nullValue())));
            assertThat(dto.getDate().getMonth(), is(not(nullValue())));
            assertThat(dto.getAccount().getId(), is(not(nullValue())));
            assertThat(dto.getAccount().getName(), is(not(nullValue())));
            assertThat(dto.getBudget().getId(), is(not(nullValue())));
            assertThat(dto.getBudget().getName(), is(not(nullValue())));
            assertThat(dto.getBudget().getBudgetCategory().getId(), is(not(nullValue())));
            assertThat(dto.getBudget().getBudgetCategory().getTypeName(), is(not(nullValue())));
            assertThat(dto.getBudget().getBudgetCategory().getName(), is(not(nullValue())));
            assertThat(dto.getBudget().getFrequencyTypeName(), is(not(nullValue())));
            assertThat(dto.getBudget().getAmount(), is(not(nullValue())));
            assertThat(dto.getBudget().getInUse(), is(not(nullValue())));
            assertThat(dto.getTransactionCategory().getId(), is(not(nullValue())));
            assertThat(dto.getTransactionCategory().getName(), is(not(nullValue())));
            assertThat(dto.getTransactionCategory().getBudgetSubCategory().getId(), is(not(nullValue())));
            assertThat(dto.getTransactionCategory().getBudgetSubCategory().getName(), is(not(nullValue())));
        }

    }
}
