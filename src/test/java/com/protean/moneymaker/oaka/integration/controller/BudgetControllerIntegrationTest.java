package com.protean.moneymaker.oaka.integration.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.protean.moneymaker.rin.dto.BudgetCategoryDto;
import com.protean.moneymaker.rin.dto.BudgetDto;
import com.protean.moneymaker.rin.model.Budget;
import com.protean.moneymaker.rin.model.BudgetCategory;
import com.protean.moneymaker.rin.repository.BudgetCategoryRepository;
import com.protean.moneymaker.rin.repository.BudgetRepository;
import com.protean.moneymaker.rin.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
class BudgetControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private BudgetCategoryRepository budgetCategoryRepository;
    @SpyBean private BudgetService budgetService;

    private static final String BASE_URI = "/v1/budgets";

    private ObjectMapper objectMapper = new ObjectMapper();
    private Set<BudgetDto> basicBudgetDtos = new HashSet<>();

    @BeforeEach
    void setUp() {
        BudgetCategoryDto categoryDto = new BudgetCategoryDto(6, "flexible", "spending");
        BudgetDto basicBudgetDto = new BudgetDto();
        basicBudgetDto.setName("TestName");
        basicBudgetDto.setBudgetCategory(categoryDto);
//        basicBudgetDto.setEndDate(ZonedDateTime.now());
//        basicBudgetDto.setStartDate(ZonedDateTime.now());
        basicBudgetDtos.add(basicBudgetDto);
    }

    @Test
    void getBudgets_GivenBudgetsExist_ThenReturnOkWithBudgetDto() throws Exception {

        mockMvc.perform(
                get(BASE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].budgetCategory.id").exists())
                .andExpect(jsonPath("$[0].budgetCategory.type").exists())
                .andExpect(jsonPath("$[0].budgetCategory.name").exists())
                .andExpect(jsonPath("$[0].budgetCategory.budgetItems").exists())
                .andExpect(jsonPath("$[0].startDate").exists())
                .andExpect(jsonPath("$[0].frequencyType").exists())
                .andExpect(jsonPath("$[0].amount").exists())
                .andExpect(jsonPath("$[0].inUse").exists());

    }

    @Test
    void getBudgets_GivenBudgetsDoNotExist_ThenReturnEmptyArray() throws Exception {

        when(budgetService.getAllActiveBudgets()).thenReturn(new HashSet<>());

        mockMvc.perform(
                get(BASE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(0)));

    }

    @Test
    void createNewBudgets_GivenSimpleBudgetProvided_ThenCreateBudgetsAndReturnWithIds() throws Exception {

        MvcResult mv = mockMvc.perform(
                post(BASE_URI)
                    .content(objectMapper.writeValueAsString(basicBudgetDtos))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].budgetCategory.id").exists())
                .andReturn();

        ArrayNode node = (ArrayNode)objectMapper.readTree(mv.getResponse().getContentAsString());
        int id = node.get(0).get("budgetCategory").get("id").intValue();

        BudgetCategory b = budgetCategoryRepository.findById(id).get();
        assertThat(b.getName().getName(), is(equalTo("spending")));
        assertThat(b.getType().getName(), is(equalTo("flexible")));

    }

    @Test
    void createNewBudgets_GivenNoBudgetProvided_ThenReturnBadRequest() throws Exception {
        mockMvc.perform(
                post(BASE_URI))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
