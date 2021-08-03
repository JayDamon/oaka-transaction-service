package com.factotum.oaka.service;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.model.TransactionCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@IntegrationTest
@WithMockUser
public class TransactionCategoryServiceImpIT {

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private BudgetService budgetService;


    @Test
    public void findAllTransactionCategories_GivenDatabaseLoadedWithValidTestData_ThenReturnListOfTransactionCategories() {
        Flux<TransactionCategory> transactionCategories = transactionCategoryService.findAllTransactionCategories();
        StepVerifier.create(transactionCategories.log()).expectNextCount(112).verifyComplete();
    }

}
