package com.factotum.transactionservice;

import com.factotum.transactionservice.http.AccountService;
import com.factotum.transactionservice.http.BudgetService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@IntegrationTest
@WithMockUser
class TransactionServiceApplicationTests {

    @MockBean
    private AccountService accountService;

    @MockBean
    private BudgetService budgetService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
    }

}

