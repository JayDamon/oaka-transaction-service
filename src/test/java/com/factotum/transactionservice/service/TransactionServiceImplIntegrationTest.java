package com.factotum.transactionservice.service;

import com.factotum.transactionservice.IntegrationTest;
import com.factotum.transactionservice.dto.BudgetCategoryDto;
import com.factotum.transactionservice.dto.BudgetDto;
import com.factotum.transactionservice.dto.ShortAccountDto;
import com.factotum.transactionservice.dto.TransactionDto;
import com.factotum.transactionservice.http.AccountService;
import com.factotum.transactionservice.http.BudgetService;
import com.factotum.transactionservice.repository.TransactionRepository;
import com.factotum.transactionservice.util.SecurityTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@IntegrationTest
class TransactionServiceImplIntegrationTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @MockBean
    private AccountService accountService;

    @MockBean
    private BudgetService budgetService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    private static final UUID budgetCategoryId = UUID.fromString("5c07c147-1aab-472b-9c8f-e500d3161210");

    @BeforeEach
    void setup() {

        List<UUID> accounts = List.of(
                UUID.fromString("43b38f84-ed18-4801-803f-2f4be3119d3f"),
                UUID.fromString("b6b59a87-a54b-40a4-bb67-c5eb90c96a35"),
                UUID.fromString("ddf60fa8-24c8-4cd1-8cf2-3572844f74b3"),
                UUID.fromString("fe961970-6a7d-4d6e-9103-627c141ce4e3"),
                UUID.fromString("318009cd-49d5-4e9d-bbdb-432b1da92ae6"),
                UUID.fromString("3a8a7955-b6aa-463f-8336-12515aef4b16"),
                UUID.fromString("cb092f82-8c71-47b1-8e5a-77929b8db57a"));

        List<ShortAccountDto> shortAccountDtos = accounts.stream().map(l -> new ShortAccountDto(l, "AccountName)")).collect(Collectors.toList());
        when(accountService.getAccounts(any())).thenReturn(Flux.fromIterable(shortAccountDtos));

        List<UUID> budgetIds = List.of(
                UUID.fromString("19735b1a-16a5-44a8-ac2f-8ef6e0efd05f"),
                UUID.fromString("b592086d-cd00-41a0-a79a-22a9ee38d385"),
                UUID.fromString("d734a5a3-3523-435f-8bdd-46d3a15c6793"),
                UUID.fromString("66de4f99-52f0-44eb-938b-229515bbf107"),
                UUID.fromString("2994618e-92d0-46b4-81ec-948e4b001060"),
                UUID.fromString("76d5ed89-f660-4c29-8b3c-963622ad05f4"),
                UUID.fromString("b919257d-f9fb-4c7f-8304-3cd4fc2b128a"),
                UUID.fromString("a75d404d-a9bf-4430-af82-549c6cac8bd6"),
                UUID.fromString("36f87c99-a3ec-4a72-9d9c-92a4045393c5"),
                UUID.fromString("b115146c-a9c2-4f2f-806f-2e9ea4a429a6"),
                UUID.fromString("739f6b39-1e2e-4818-98b4-223abe0cf332"),
                UUID.fromString("f89a3a6e-e422-4f19-af70-22a650bec2bb"),
                UUID.fromString("d3197c02-552a-461e-b1e1-5007295ec4d6"),
                UUID.fromString("31e92edd-8803-4762-b4e5-f523fc09deef"),
                UUID.fromString("5d96fecb-a47b-4dbd-b4e9-c48662607578"),
                UUID.fromString("9f2fd26e-593a-4704-a501-eaf001e52c7c"),
                UUID.fromString("87e7c121-0fc4-468b-9470-b361fad5d7a6"),
                UUID.fromString("cc13b3f9-0d59-49ee-9024-904b9f385a08"),
                UUID.fromString("eef4b453-84ed-46f9-95a3-1a324acca97c"),
                UUID.fromString("33c31199-202a-4443-8f09-417ea8acd7d3"),
                UUID.fromString("8a9a8570-6e1d-4c5a-b897-6be3cefe69c4"),
                UUID.fromString("b5d96fbf-ef10-4be7-80b2-44f84cb10b32"),
                UUID.fromString("f4e3fd3d-aa87-452b-b2d5-2af84185b5cf"),
                UUID.fromString("c5f4c3de-ed1e-4dc2-af1c-38bf10fa56f3"),
                UUID.fromString("82b5565c-71a6-4213-9adb-c15cec93b5c8"),
                UUID.fromString("9322efc2-39e7-4e71-bd6b-bfdd933ddd75"),
                UUID.fromString("b72b9a68-30f7-455f-8222-700b3ac39d35"),
                UUID.fromString("058d08aa-9f03-42d2-88e9-1b707a6a8438"),
                UUID.fromString("2d9acab8-7412-4232-a1db-79ae83765cce"),
                UUID.fromString("5de78a99-c5b6-41c0-a643-c8d9e6cdbb57"),
                UUID.fromString("0f421834-ca25-4ed8-bc1d-60ac93ae6391"));

        List<BudgetDto> budgetDtos = budgetIds.stream().map(l -> {
            BudgetDto budget = new BudgetDto();
            budget.setId(l);
            budget.setName("TestName");
            budget.setFrequencyTypeName("FrequencyType");
            budget.setAmount(BigDecimal.valueOf(22.45));
            budget.setInUse(false);
            BudgetCategoryDto budgetCategoryDto = new BudgetCategoryDto();
            budgetCategoryDto.setId(budgetCategoryId);
            budgetCategoryDto.setName("BudgetCategoryName");
            budgetCategoryDto.setTypeName("Type");
            budget.setBudgetCategory(budgetCategoryDto);
            return budget;
        }).collect(Collectors.toList());
        when(budgetService.getBudgets(any())).thenReturn(Flux.fromIterable(budgetDtos));
    }

    @Test
    void getAllTransactionDtos() {

        Flux<TransactionDto> transactions = transactionService.getAllTransactionDtos(SecurityTestUtil.getTestJwt());

        List<TransactionDto> dtos = transactions.collectList().block();

        assertThat(dtos, hasSize(greaterThanOrEqualTo(891)));

        UUID transactionId = UUID.fromString("82516837-6a0a-4627-b7a3-5554c16e5e54");

        TransactionDto dto = dtos.stream().filter(t -> t.getId().equals(transactionId)).toList().get(0);

        if (dto.getId().equals(transactionId)) {
            assertThat(dto.getId(), is(not(nullValue())));
            assertThat(dto.getAmount(), is(not(nullValue())));
            assertThat(dto.getDescription(), is(not(nullValue())));
            assertThat(dto.getDate().getMonth(), is(not(nullValue())));
            assertThat(dto.getAccount().getId(), is(not(nullValue())));
            assertThat(dto.getAccount().getName(), is(not(nullValue())));
            assertThat(dto.getBudget().getId(), is(not(nullValue())));
            assertThat(dto.getBudget().getName(), is(not(nullValue())));
            assertThat(dto.getBudget().getBudgetCategory().getId(), is(not(nullValue())));
            assertThat(dto.getBudget().getBudgetCategory().getTypeName(), is(not(nullValue())));
            assertThat(dto.getBudget().getBudgetCategory().getName(), is(not(nullValue())));
            assertThat(dto.getBudget().getFrequencyTypeName(), is(not(nullValue())));
            assertThat(dto.getBudget().getAmount(), is(not(nullValue())));
            assertThat(dto.getBudget().getInUse(), is(not(nullValue())));
            assertThat(dto.getTransactionCategory().getId(), is(not(nullValue())));
            assertThat(dto.getTransactionCategory().getName(), is(not(nullValue())));
            assertThat(dto.getTransactionCategory().getBudgetSubCategory().getId(), is(not(nullValue())));
            assertThat(dto.getTransactionCategory().getBudgetSubCategory().getName(), is(not(nullValue())));
        }

    }
}
