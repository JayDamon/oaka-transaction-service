package com.factotum.oaka.service;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.model.TransactionCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@IntegrationTest
public class TransactionCategoryServiceImpIT {

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @Test
    public void findAllTransactionCategories_GivenDatabaseLoadedWithValidTestData_ThenReturnListOfTransactionCategories() {
        Flux<TransactionCategory> transactionCategories = transactionCategoryService.findAllTransactionCategories();
        StepVerifier.create(transactionCategories.log()).expectNextCount(112).verifyComplete();
    }

}
