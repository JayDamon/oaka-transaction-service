package com.factotum.oaka.controller;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.repository.TransactionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class TransactionControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TransactionRepository transactionRepository;

    private static final String BASE_URI = "/v1/transactions";

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void getAllTransactions() throws Exception {

        this.mockMvc.perform(
                get(BASE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].amount").exists())
                .andExpect(jsonPath("$[0].description").exists())
                .andExpect(jsonPath("$[0].date").exists())
                .andExpect(jsonPath("$[0].category").exists())
                .andExpect(jsonPath("$[0].category.id").exists())
                .andExpect(jsonPath("$[0].category.name").exists())
                .andExpect(jsonPath("$[0].account").exists())
                .andExpect(jsonPath("$[0].account.id").exists())
                .andExpect(jsonPath("$[0].account.name").exists())
                .andExpect(jsonPath("$[0].budget").exists())
                .andExpect(jsonPath("$[0].budget.id").exists())
                .andExpect(jsonPath("$[0].budget.name").exists())
                .andExpect(jsonPath("$[0].budget.budgetCategory").exists())
                .andExpect(jsonPath("$[0].budget.budgetCategory.id").exists())
                .andExpect(jsonPath("$[0].budget.budgetCategory.type").exists())
                .andExpect(jsonPath("$[0].budget.budgetCategory.name").exists());
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

    @Test
    void saveNewTransactions_GivenValidTransactionsProvided_ThenSaveTransactionsAndReturnOk() throws Exception {

        ShortAccountDto accountDto = new ShortAccountDto();
        accountDto.setId(1L);

        BudgetDto budget = new BudgetDto();
        budget.setId(1L);

        TransactionDto t1 = new TransactionDto();
        t1.setAccount(accountDto);
        t1.setAmount(BigDecimal.valueOf(451.21));
        t1.setDescription("Description 1");
        t1.setDate(ZonedDateTime.now());
        t1.setBudget(budget);

        TransactionDto t2 = new TransactionDto();
        t2.setAccount(accountDto);
        t2.setAmount(BigDecimal.valueOf(56.22));
        t2.setDescription("Description 564");
        t2.setDate(ZonedDateTime.now().minusDays(10));
        t2.setBudget(budget);

        MvcResult ra = mockMvc.perform(
                post(BASE_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(Arrays.asList(t1, t2))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", hasSize(2)))
                .andReturn();

        JsonNode json = this.objectMapper.readTree(ra.getResponse().getContentAsString());
        assertThat(json.isArray(), is(true));

        int itemsToCount = 0;

        for (JsonNode n : json) {
            TransactionDto tran = this.objectMapper.readValue(n.toString(), TransactionDto.class);
            assertThat(this.transactionRepository.existsById(tran.getId()), is(true));
            itemsToCount++;
        }

        assertThat(itemsToCount, is(equalTo(json.size())));

    }
}
