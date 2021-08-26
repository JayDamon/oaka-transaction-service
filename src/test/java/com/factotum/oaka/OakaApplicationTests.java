package com.factotum.oaka;

import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@IntegrationTest
@WithMockUser
class OakaApplicationTests {

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

