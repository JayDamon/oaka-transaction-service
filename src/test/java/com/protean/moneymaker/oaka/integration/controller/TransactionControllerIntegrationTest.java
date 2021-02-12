package com.protean.moneymaker.oaka.integration.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.protean.moneymaker.oaka.integration.IntegrationTest;
import com.protean.moneymaker.rin.dto.BudgetDto;
import com.protean.moneymaker.rin.dto.ShortAccountDto;
import com.protean.moneymaker.rin.dto.TransactionDto;
import com.protean.moneymaker.rin.model.Transaction;
import com.protean.moneymaker.rin.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
class TransactionControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private TransactionRepository transactionRepository;

    private static final String BASE_URI = "/v1/transactions";

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

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
