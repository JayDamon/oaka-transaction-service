package com.factotum.transactionservice.controller;

import com.factotum.transactionservice.IntegrationTest;
import com.factotum.transactionservice.dto.BudgetCategoryDto;
import com.factotum.transactionservice.dto.BudgetDto;
import com.factotum.transactionservice.dto.ShortAccountDto;
import com.factotum.transactionservice.dto.TransactionDto;
import com.factotum.transactionservice.enumeration.BudgetType;
import com.factotum.transactionservice.http.AccountService;
import com.factotum.transactionservice.http.BudgetService;
import com.factotum.transactionservice.repository.TransactionRepository;
import com.factotum.transactionservice.util.SecurityTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@IntegrationTest
class TransactionControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AccountService accountService;

    @MockBean
    private BudgetService budgetService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    private static final String URI = "/v1/transactions";

    private static final UUID budgetCategoryIdOne = UUID.fromString("5c07c147-1aab-472b-9c8f-e500d3161210");
    private static final UUID accountIdThree = UUID.fromString("ddf60fa8-24c8-4cd1-8cf2-3572844f74b3");
    private static final UUID budgetIdFive = UUID.fromString("2994618e-92d0-46b4-81ec-948e4b001060");
    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
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
            budgetCategoryDto.setId(budgetCategoryIdOne);
            budgetCategoryDto.setName("BudgetCategoryName");
            budgetCategoryDto.setTypeName("Type");
            budget.setBudgetCategory(budgetCategoryDto);
            return budget;
        }).collect(Collectors.toList());
        when(budgetService.getBudgets(any())).thenReturn(Flux.fromIterable(budgetDtos));
    }

    // getAllTransactions
    @Test
    void getAllTransactions_GivenTransactionsExist_ThenReturnTransactions() {
        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .get()
                .uri(URI)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").exists()
                .jsonPath("$[0].amount").exists()
                .jsonPath("$[0].description").exists()
                .jsonPath("$[0].date").exists()
                .jsonPath("$[0].account").exists()
                .jsonPath("$[0].account.id").exists()
                .jsonPath("$[0].account.name").exists()
                .jsonPath("$[0].budget").exists()
                .jsonPath("$[0].budget.id").exists()
                .jsonPath("$[0].budget.name").exists()
                .jsonPath("$[0].budget.budgetCategory").exists()
                .jsonPath("$[0].budget.budgetCategory.id").exists()
                .jsonPath("$[0].budget.budgetCategory.type").exists()
                .jsonPath("$[0].budget.budgetCategory.name").exists();
    }

    // createNewTransactions
    @Test
    @WithMockUser
    void createNewTransactions_GivenSingleTransactionProvided_ThenCreateTransaction() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(accountIdThree);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(budgetIdFive);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setDescription("Transaction Description");
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .post()
                .uri(URI)
                .body(Flux.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$[0].id").exists()
                .jsonPath("$[0].amount").isEqualTo(22.3)
                .jsonPath("$[0].description").isEqualTo("Transaction Description")
                .jsonPath("$[0].date").exists()
                .jsonPath("$[0].account.id").isEqualTo(accountIdThree.toString())
                .jsonPath("$[0].budget.id").isEqualTo(budgetIdFive.toString());

    }

    @Test
    @WithMockUser
    void createNewTransactions_GivenTransactionIsMissingAccountId_ThenReturnBadRequest() {

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(budgetIdFive);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setDescription("Transaction Description");
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .post()
                .uri(URI)
                .body(Flux.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    @WithMockUser
    void createNewTransactions_GivenAmountIsNotProvided_ThenReturnBadRequest() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(accountIdThree);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(budgetIdFive);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setDescription("Transaction Description");

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .post()
                .uri(URI)
                .body(Flux.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    @WithMockUser
    void createNewTransactions_GivenDescriptionIsNotProvided_ThenReturnBadRequest() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(accountIdThree);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(budgetIdFive);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .post()
                .uri(URI)
                .body(Flux.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    @WithMockUser
    void createNewTransactions_GivenDateIsNotProvided_ThenReturnBadRequest() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(accountIdThree);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(budgetIdFive);

        TransactionDto tn = new TransactionDto();
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDescription("A Description");
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .post()
                .uri(URI)
                .body(Flux.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isBadRequest();

    }

    // updateTransaction
    @Test
    void updateTransaction_GivenValidTransaction_ThenReturnOk() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(accountIdThree);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(budgetIdFive);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setId(UUID.fromString("82516837-6a0a-4627-b7a3-5554c16e5e54"));
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setDescription("Transaction Description");
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .patch()
                .uri(URI + "/{id}", tn.getId())
                .body(Mono.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void updateTransaction_GivenTransactionDoesNotExist_ThenReturnNotFound() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(accountIdThree);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(budgetIdFive);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setId(UUID.randomUUID());
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setDescription("Transaction Description");
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt("User_With_No_Transactions")))
                .patch()
                .uri(URI + "/{id}", tn.getId())
                .body(Mono.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateTransaction_GivenPathIdAndDtoIdDoNotMatch_ThenReturnBadRequest() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(accountIdThree);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(budgetIdFive);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setId(UUID.randomUUID());
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setDescription("Transaction Description");
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .patch()
                .uri(URI + "/{id}", UUID.randomUUID())
                .body(Mono.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateTransaction_GivenTransactionIdNotProvidedInBody_ThenReturnBadRequest() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(accountIdThree);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(budgetIdFive);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setDescription("Transaction Description");
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .patch()
                .uri(URI + "/{id}", UUID.randomUUID())
                .body(Mono.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    // getTransactionTotal
    @Test
    @WithMockUser
    void getTransactionTotal_GiveTransactionsExistForBudgets_ThenReturnTotalAmount() {

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(URI + "/total")
                                .queryParam("year", "2017")
                                .queryParam("month", "1")
                                .queryParam("budgetType", BudgetType.EXPENSE)
                                .queryParam("budgetIds", "b115146c-a9c2-4f2f-806f-2e9ea4a429a6, 739f6b39-1e2e-4818-98b4-223abe0cf332, f89a3a6e-e422-4f19-af70-22a650bec2bb, d3197c02-552a-461e-b1e1-5007295ec4d6, 31e92edd-8803-4762-b4e5-f523fc09deef, 5d96fecb-a47b-4dbd-b4e9-c48662607578, 9322efc2-39e7-4e71-bd6b-bfdd933ddd75, 9f2fd26e-593a-4704-a501-eaf001e52c7c, 87e7c121-0fc4-468b-9470-b361fad5d7a6, cc13b3f9-0d59-49ee-9024-904b9f385a08, b72b9a68-30f7-455f-8222-700b3ac39d35, eef4b453-84ed-46f9-95a3-1a324acca97c, 2d9acab8-7412-4232-a1db-79ae83765cce, 2d9acab8-7412-4232-a1db-79ae83765cce, 5de78a99-c5b6-41c0-a643-c8d9e6cdbb57")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.total").exists()
                .jsonPath("$.budgetType").exists();

    }

    @Test
    @WithMockUser
    void getTransactionTotal_GiveTransactionsExistForBudgetsTwenty_ThenReturnTotalAmount() {

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(URI + "/total")
                                .queryParam("year", "2017")
                                .queryParam("month", "1")
                                .queryParam("budgetType", BudgetType.INCOME)
                                .queryParam("budgetIds", "33c31199-202a-4443-8f09-417ea8acd7d3")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.budgetType").exists();

    }

    @Test
    @WithMockUser
    void getTransactionTotal_GiveTransactionsExistForBudgetsThree_ThenReturnTotalAmount() {

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(URI + "/total")
                                .queryParam("year", "2017")
                                .queryParam("month", "1")
                                .queryParam("budgetType", BudgetType.EXPENSE)
                                .queryParam("budgetIds", "19735b1a-16a5-44a8-ac2f-8ef6e0efd05f, b592086d-cd00-41a0-a79a-22a9ee38d385, d734a5a3-3523-435f-8bdd-46d3a15c6793, 66de4f99-52f0-44eb-938b-229515bbf107, 2994618e-92d0-46b4-81ec-948e4b001060, 76d5ed89-f660-4c29-8b3c-963622ad05f4, 8a9a8570-6e1d-4c5a-b897-6be3cefe69c4, b5d96fbf-ef10-4be7-80b2-44f84cb10b32, 87e7c121-0fc4-468b-9470-b361fad5d7a6")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.budgetType").exists();

    }

    @Test
    @WithMockUser
    void getTransactionTotal_GiveNoTransactionsExist_ThenReturnTotalAmount() {

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(URI + "/total")
                                .queryParam("year", "2017")
                                .queryParam("month", "1")
                                .queryParam("budgetType", BudgetType.INCOME)
                                .queryParam("budgetIds", "f4e3fd3d-aa87-452b-b2d5-2af84185b5cf")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.budgetType").exists();

    }

    @Test
    @WithMockUser
    void getTransactionTotal_GiveTransactionsExistForBudgetsSix_ThenReturnTotalAmount() {

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .get()
                .uri(uriBuilder -> uriBuilder.path(URI + "/total")
                        .queryParam("year", "2017")
                        .queryParam("month", "1")
                        .queryParam("budgetType", BudgetType.EXPENSE)
                        .queryParam("budgetIds", "c5f4c3de-ed1e-4dc2-af1c-38bf10fa56f3, b919257d-f9fb-4c7f-8304-3cd4fc2b128a, a75d404d-a9bf-4430-af82-549c6cac8bd6, 36f87c99-a3ec-4a72-9d9c-92a4045393c5, 82b5565c-71a6-4213-9adb-c15cec93b5c8, 76d5ed89-f660-4c29-8b3c-963622ad05f4").build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.budgetType").exists();

    }

}
