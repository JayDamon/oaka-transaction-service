package com.protean.moneymaker.oaka.integration.controller;

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

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TransactionControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;

    private static final String BASE_URI = "/v1/transactions";

    @Test
    void getAllTransactions() throws Exception {

        // FIXME need to update so it does not include all the extra unecessary stuff
        this.mockMvc.perform(
                get(BASE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].amount").exists())
                .andExpect(jsonPath("$[0].description").exists())
                .andExpect(jsonPath("$[0].date").exists())
                .andExpect(jsonPath("$[0].account").exists())
                .andExpect(jsonPath("$[0].account.id").exists())
                .andExpect(jsonPath("$[0].account.name").exists())
                .andExpect(jsonPath("$[0].budget").exists())
                .andExpect(jsonPath("$[0].budget.id").exists())
                .andExpect(jsonPath("$[0].budget.name").exists())
                .andExpect(jsonPath("$[0].budget.budgetCategory").exists())
                .andExpect(jsonPath("$[0].budget.budgetCategory.id").exists())
                .andExpect(jsonPath("$[0].budget.budgetCategory.type").exists())
                .andExpect(jsonPath("$[0].budget.budgetCategory.name").exists())
                .andExpect(jsonPath("$[0].budget.startDate").exists())
//                .andExpect(jsonPath("$[0].budget.endDate").exists())
                .andExpect(jsonPath("$[0].budget.frequencyType").exists())
                .andExpect(jsonPath("$[0].budget.amount").exists())
                .andExpect(jsonPath("$[0].budget.inUse").exists())
                .andExpect(jsonPath("$[0].category").exists())
                .andExpect(jsonPath("$[0].category.id").exists())
                .andExpect(jsonPath("$[0].category.name").exists())
                .andExpect(jsonPath("$[0].category.subCategory").exists())
                .andExpect(jsonPath("$[0].category.subCategory.id").exists())
                .andExpect(jsonPath("$[0].category.subCategory.name").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.id").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.name").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.account").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.account.id").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.account.nae").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.subCategory").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.subCategory.id").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.subCategory.name").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.transactionCategory").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.transactionCategory.id").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.transactionCategory.name").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.transactionCategory.subCategory").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.transactionCategory.subCategory.id").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.transactionCategory.subCategory.name").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.transactionCategory.subCategory.transactionType").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.frequencyType").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.frequency").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.occurrence").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.startDate").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.endDate").exists())
//                .andExpect(jsonPath("$[0].recurringTransaction.amount").exists())
                ;
    }

    @Test
    void getTransactionCategories_GivenCategoriesExist_ThenReturnAllCategories() throws Exception {

        mockMvc.perform(
                get(BASE_URI + "/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].subCategory").exists())
                .andExpect(jsonPath("$[0].subCategory.id").exists())
                .andExpect(jsonPath("$[0].subCategory.name").exists());

    }
}