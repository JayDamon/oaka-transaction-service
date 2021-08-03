package com.factotum.oaka;

import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@IntegrationTest
@WithMockUser
class OakaApplicationTests {

    @MockBean
    private AccountService accountService;

    @MockBean
    private BudgetService budgetService;

    @Test
    void contextLoads() {
    }

}

