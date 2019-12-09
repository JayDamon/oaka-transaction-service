package com.protean.moneymaker.oaka.integration.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.protean.moneymaker.rin.dto.AccountClassificationDto;
import com.protean.moneymaker.rin.dto.AccountDto;
import com.protean.moneymaker.rin.dto.AccountTypeDto;
import com.protean.moneymaker.rin.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
class AccountControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;

    private static final String BASE_URI = "/v1/accounts";

    private static ObjectMapper mapper = new ObjectMapper();

    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        AccountTypeDto accountTypeDto = new AccountTypeDto(2, "Savings", "Savings");

        AccountClassificationDto classificationDto = new AccountClassificationDto(2, "Liability");

        accountDto = new AccountDto(
                1L, "NewName", accountTypeDto, BigDecimal.valueOf(500.01),
                BigDecimal.valueOf(200), classificationDto, false, false);
    }

    @Test
    void getAccounts_GivenAccountsExist_ThenReturnAccounts() throws Exception {

        mockMvc.perform(
                get(BASE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasSize(6)))
                .andExpect(jsonPath("$[*].name", hasSize(6)))
                .andExpect(jsonPath("$[*].type.id", hasSize(6)))
                .andExpect(jsonPath("$[*].type.fullName", hasSize(6)))
                .andExpect(jsonPath("$[*].type.shortName", hasSize(6)))
                .andExpect(jsonPath("$[*].startingBalance", hasSize(6)))
                .andExpect(jsonPath("$[*].currentBalance", hasSize(6)))
                .andExpect(jsonPath("$[*].classification.id", hasSize(6)))
                .andExpect(jsonPath("$[*].classification.name", hasSize(6)))
                .andExpect(jsonPath("$[*].isPrimary", hasSize(6)))
                .andExpect(jsonPath("$[*].isInCashFlow", hasSize(6)));

    }

    @Test
    void createNewAccount_GivenValidJson_ThenReturnAccounts() throws Exception {

        this.accountDto.setId(null);
        this.accountDto.setCurrentbalance(null);

        mockMvc.perform(
                post(BASE_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(this.accountDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(greaterThan(0))))
                .andExpect(jsonPath("$.name", is(equalTo(this.accountDto.getName()))))
                .andExpect(jsonPath("$.startingBalance", is(equalTo(this.accountDto.getStartingBalance().doubleValue()))))
                .andExpect(jsonPath("$.isPrimary", is(equalTo(this.accountDto.getPrimary()))))
                .andExpect(jsonPath("$.isInCashFlow", is(equalTo(this.accountDto.getInCashFlow()))))
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.type.id", is(equalTo(this.accountDto.getType().getId()))))
                .andExpect(jsonPath("$.classification").exists())
                .andExpect(jsonPath("$.classification.id", is(equalTo(this.accountDto.getClassification().getId()))));

    }

    @Test
    void updateAccount_GivenAccountExists_ThenUpdateItsValuesAndReturn() throws Exception {

        mockMvc.perform(
                patch(BASE_URI + "/{id}", this.accountDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(this.accountDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(equalTo(this.accountDto.getId().intValue()))))
                .andExpect(jsonPath("$.name", is(equalTo(this.accountDto.getName()))))
                .andExpect(jsonPath("$.startingBalance", is(equalTo(this.accountDto.getStartingBalance().doubleValue()))))
                .andExpect(jsonPath("$.currentBalance", is(equalTo(8000.56))))
                .andExpect(jsonPath("$.isPrimary", is(equalTo(this.accountDto.getPrimary()))))
                .andExpect(jsonPath("$.isInCashFlow", is(equalTo(this.accountDto.getInCashFlow()))))
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.type.id", is(equalTo(this.accountDto.getType().getId()))))
                .andExpect(jsonPath("$.classification").exists())
                .andExpect(jsonPath("$.classification.id", is(equalTo(this.accountDto.getClassification().getId()))));

    }

    @Test
    void updateAccount_GivenAccountIdMissing_ThenReturnBadReqeust() throws Exception {

        this.accountDto.setId(null);

        mockMvc.perform(
                patch(BASE_URI + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(this.accountDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateAccount_GivenAccountDoesNotExist_ThenReturnBadReqeust() throws Exception {

        this.accountDto.setId(8021L);

        mockMvc.perform(
                patch(BASE_URI + "/{id}", this.accountDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(this.accountDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateAccount_GivenPathIdDoesNotMatchBodyId_ThenReturnBadRequest() throws Exception {

        this.accountDto.setId(2L);

        mockMvc.perform(
                patch(BASE_URI + "/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(this.accountDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void getAccountTypes_GivenTypesExist_ThenReturnAllTypes() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_URI + "/types"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", hasSize(greaterThan(0))))
                .andReturn();

        JsonNode allValues = mapper.readTree(result.getResponse().getContentAsString());

        for (JsonNode n : allValues) {

            String json = n.toString();

            assertThat(json, hasJsonPath("$.id"));
            assertThat(json, hasJsonPath("$.fullName"));
            assertThat(json, hasJsonPath("$.shortName"));

        }
    }

    @Test
    void getAccountClassification_GivenClassificationsExist_ThenReturnAllTypes() throws Exception {

        MvcResult result = mockMvc.perform(
                get(BASE_URI + "/classifications"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]", hasSize(greaterThan(0))))
                .andReturn();

        JsonNode allValues = mapper.readTree(result.getResponse().getContentAsString());

        for (JsonNode n : allValues) {

            String json = n.toString();

            assertThat(json, hasJsonPath("$.id"));
            assertThat(json, hasJsonPath("$.name"));

        }

    }

}
