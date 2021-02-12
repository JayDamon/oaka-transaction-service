package com.protean.moneymaker.oaka.integration.controller;

import com.protean.moneymaker.oaka.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class BudgetTypeControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;

    private static final String BASE_URI = "/v1/budgetTypes";

    @Test
    void getAllBudgetCategories_GivenDataExists_ThenReturnValidBudgetCategories() throws Exception {
        mockMvc.perform(
                get(BASE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").exists())
                .andExpect(jsonPath("[0].type").exists())
                .andExpect(jsonPath("[0].budgetCategories").exists())
                .andExpect(jsonPath("[0].budgetCategories[0].id").exists())
                .andExpect(jsonPath("[0].budgetCategories[0].name").exists())
                .andExpect(jsonPath("[0].budgetCategories[0].type").doesNotExist())
                .andExpect(jsonPath("[0].budgetCategories[0].budgetItems").exists())
                .andExpect(jsonPath("[0].budgetCategories[0].budgetItems[0].id").exists())
                .andExpect(jsonPath("[0].budgetCategories[0].budgetItems[0].category").exists())
                .andExpect(jsonPath("[0].budgetCategories[0].budgetItems[0].name").exists());

    }

}
