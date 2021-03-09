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
        StepVerifier.create(dtos.log())
                .expectNextMatches(dto -> dto.getBudget().getName() != null)
                .expectNextMatches(dto -> dto.getId() != null)
                .expectNextMatches(dto -> dto.getAmount() != null)
                .expectNextMatches(dto -> dto.getDescription() != null)
                .expectNextMatches(dto -> dto.getDate().getMonth() != null)
                .expectNextMatches(dto -> dto.getAccount().getId() != null)
                .expectNextMatches(dto -> dto.getAccount().getName() != null)
                .expectNextMatches(dto -> dto.getBudget().getId() != null)
                .expectNextMatches(dto -> dto.getBudget().getName() != null)
                .expectNextMatches(dto -> dto.getBudget().getBudgetCategory().getId() != null)
                .expectNextMatches(dto -> dto.getBudget().getBudgetCategory().getTypeName() != null)
                .expectNextMatches(dto -> dto.getBudget().getBudgetCategory().getName() != null)
                .expectNextMatches(dto -> dto.getBudget().getFrequencyTypeName() != null)
                .expectNextMatches(dto -> dto.getBudget().getAmount() != null)
                .expectNextMatches(dto -> dto.getBudget().getInUse() != null)
                .expectNextMatches(dto -> dto.getTransactionCategory().getId() != null)
                .expectNextMatches(dto -> dto.getTransactionCategory().getName() != null)
                .expectNextMatches(dto -> dto.getTransactionCategory().getBudgetSubCategory().getId() != null)
                .expectNextMatches(dto -> dto.getTransactionCategory().getBudgetSubCategory().getName() != null)
                .expectNextCount(891)
                .verifyComplete();
    }
}
