package com.factotum.oaka.service;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.TransactionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@IntegrationTest
class TransactionServiceImplT {

    @Autowired
    private TransactionService transactionService;

    @Test
    void getAllTransactionDtos() {

        Flux<TransactionDto> dtos = transactionService.getAllTransactionDtos();

        // Assert
        StepVerifier.create(dtos.log()).expectNextCount(891).verifyComplete();
//        assertThat(dtos, hasSize(891));
//
//        boolean dtoChecked = false;
//        for (TransactionDto dto : dtos) {
//            if (dto.getId().equals(2L)) {
//                assertThat(dto.getId(), is(not(nullValue())));
//                assertThat(dto.getAmount(), is(not(nullValue())));
//                assertThat(dto.getDescription(), is(not(nullValue())));
//                assertThat(dto.getDate().getMonth(), is(not(nullValue())));
//                assertThat(dto.getAccount().getId(), is(not(nullValue())));
//                assertThat(dto.getAccount().getName(), is(not(nullValue())));
//                assertThat(dto.getBudget().getId(), is(not(nullValue())));
//                assertThat(dto.getBudget().getName(), is(not(nullValue())));
//                assertThat(dto.getBudget().getBudgetCategory().getId(), is(not(nullValue())));
//                assertThat(dto.getBudget().getBudgetCategory().getTypeName(), is(not(nullValue())));
//                assertThat(dto.getBudget().getBudgetCategory().getName(), is(not(nullValue())));
//                assertThat(dto.getBudget().getFrequencyTypeName(), is(not(nullValue())));
//                assertThat(dto.getBudget().getAmount(), is(not(nullValue())));
//                assertThat(dto.getBudget().getInUse(), is(not(nullValue())));
//                assertThat(dto.getTransactionCategory().getId(), is(not(nullValue())));
//                assertThat(dto.getTransactionCategory().getName(), is(not(nullValue())));
//                assertThat(dto.getTransactionCategory().getBudgetSubCategory().getId(), is(not(nullValue())));
//                assertThat(dto.getTransactionCategory().getBudgetSubCategory().getName(), is(not(nullValue())));
//                dtoChecked = true;
//                break;
//            }
//        }
//        assertThat(dtoChecked, is(true));
    }
}
