package com.protean.moneymaker.oaka.service;

import com.protean.moneymaker.oaka.dto.TransactionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@ActiveProfiles("test")
class TransactionServiceImplIntegrationTest {

    @Autowired private TransactionService transactionService;

    @Test
    void getAllTransactionDtos() {

        Set<TransactionDto> dtos = transactionService.getAllTransactionDtos();

        // Assert
        assertThat(dtos, hasSize(891));

        boolean dtoChecked = false;
        for (TransactionDto dto : dtos) {
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
                assertThat(dto.getBudget().getStartDate().getMonth(), is(not(nullValue())));
                assertThat(dto.getBudget().getEndDate().getMonth(), is(not(nullValue())));
                assertThat(dto.getBudget().getFrequencyTypeName(), is(not(nullValue())));
                assertThat(dto.getBudget().getAmount(), is(not(nullValue())));
                assertThat(dto.getBudget().getInUse(), is(not(nullValue())));
                assertThat(dto.getTransactionCategory().getId(), is(not(nullValue())));
                assertThat(dto.getTransactionCategory().getName(), is(not(nullValue())));
                assertThat(dto.getTransactionCategory().getBudgetSubCategory().getId(), is(not(nullValue())));
                assertThat(dto.getTransactionCategory().getBudgetSubCategory().getName(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getId(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getRecurringTransactionName(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getAccount().getId(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getAccount().getName(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getBudgetSubCategory().getId(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getBudgetSubCategory().getName(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getTransactionCategory().getId(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getTransactionCategory().getName(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getTransactionCategory().getBudgetSubCategory().getId(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getTransactionCategory().getBudgetSubCategory().getName(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getFrequencyTypeName(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getFrequency(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getOccurrenceName(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getTransactionTypeName(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getStartDate().getMonth(), is(not(nullValue())));
//                assertThat(dto.getRecurringTransaction().getEndDate().monthOfYear(), is(not(nullValue())));
                assertThat(dto.getRecurringTransaction().getAmount(), is(not(nullValue())));
                dtoChecked = true;
                break;
            }
        }
        assertThat(dtoChecked, is(true));
    }
}
