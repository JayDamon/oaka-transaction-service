package com.factotum.oaka.service;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.BudgetCategoryDto;
import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.util.SecurityTestUtil;
import org.apache.commons.collections4.map.HashedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
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
        when(accountService.getAccounts(any())).thenReturn(Flux.fromIterable(shortAccountDtos));

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
        when(budgetService.getBudgets(any())).thenReturn(Flux.fromIterable(budgetDtos));
    }

    @Test
    void getAllTransactionDtos_ReturnsTestTransactionsAsDtos() {

        Flux<TransactionDto> transactionDtos = transactionService.getAllTransactionDtos(SecurityTestUtil.getTestJwt());
        StepVerifier.create(transactionDtos.log()).expectNextCount(891).verifyComplete();

    }

}