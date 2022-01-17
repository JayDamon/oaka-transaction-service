package com.factotum.transactionservice.service;

import com.factotum.transactionservice.IntegrationTest;
import com.factotum.transactionservice.http.AccountService;
import com.factotum.transactionservice.http.BudgetService;
import com.factotum.transactionservice.model.TransactionCategory;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@IntegrationTest
@WithMockUser
public class TransactionCategoryServiceImpIntegrationTest {

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private BudgetService budgetService;

    @MockBean
    private RabbitTemplate rabbitTemplate;


    @Test
    public void findAllTransactionCategories_GivenDatabaseLoadedWithValidTestData_ThenReturnListOfTransactionCategories() {
        Flux<TransactionCategory> transactionCategories = transactionCategoryService.findAllTransactionCategories();
        StepVerifier.create(transactionCategories.log()).expectNextCount(112).verifyComplete();
    }

}
