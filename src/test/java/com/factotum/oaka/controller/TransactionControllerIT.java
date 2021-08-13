package com.factotum.oaka.controller;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.BudgetCategoryDto;
import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.enumeration.BudgetType;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.util.SecurityTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@IntegrationTest
class TransactionControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AccountService accountService;

    @MockBean
    private BudgetService budgetService;

    private static final String URI = "/v1/transactions";

    @BeforeEach
    void setUp() {

        List<ShortAccountDto> shortAccountDtos = LongStream.range(0, 40).mapToObj(l -> new ShortAccountDto(l, "AccountName)")).collect(Collectors.toList());
        when(accountService.getAccounts(any())).thenReturn(Flux.fromIterable(shortAccountDtos));

        List<BudgetDto> budgetDtos = LongStream.range(0, 40).mapToObj(l -> {
            BudgetDto budget = new BudgetDto();
            budget.setId(l);
            budget.setName("TestName");
            budget.setFrequencyTypeName("FrequencyType");
            budget.setAmount(BigDecimal.valueOf(22.45));
            budget.setInUse(false);
            BudgetCategoryDto budgetCategoryDto = new BudgetCategoryDto();
            budgetCategoryDto.setId(1);
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
        shortAccountDto.setId(3L);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

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
                .jsonPath("$[0].account.id").isEqualTo(3L)
                .jsonPath("$[0].budget.id").isEqualTo(5L);

    }

    @Test
    @WithMockUser
    void createNewTransactions_GivenTransactionIsMissingAccountId_ThenReturnBadRequest() {

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

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
        shortAccountDto.setId(3L);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

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
        shortAccountDto.setId(3L);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

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
        shortAccountDto.setId(3L);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

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
        shortAccountDto.setId(3L);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setId(1L);
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
        shortAccountDto.setId(3L);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setId(1L);
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
        shortAccountDto.setId(3L);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

        LocalDate tnDate = LocalDate.of(2021, 2, 3);

        TransactionDto tn = new TransactionDto();
        tn.setId(1L);
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setDescription("Transaction Description");
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
                .mutateWith(mockJwt().jwt(SecurityTestUtil.getTestJwt()))
                .patch()
                .uri(URI + "/{id}", 2L)
                .body(Mono.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateTransaction_GivenTransactionIdNotProvidedInBody_ThenReturnBadRequest() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(3L);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

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
                .uri(URI + "/{id}", 2L)
                .body(Mono.just(tn), TransactionDto.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    // getTransactionCategories
    @Test
    @WithMockUser
    void getTransactionCategories_GivenCategoriesExist_ThenReturnAllCategories() {

        webTestClient.get().uri(URI + "/categories")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").exists()
                .jsonPath("$[0].name").exists()
                .jsonPath("$[0].subCategory").exists()
                .jsonPath("$[0].subCategory.id").exists()
                .jsonPath("$[0].subCategory.name").exists();

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
                                .queryParam("budgetIds", "10, 11, 12, 13, 14, 15, 26, 16, 17, 18, 27, 19, 28, 29, 30")
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
                                .queryParam("budgetIds", "20")
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
                                .queryParam("budgetIds", "1, 2, 3, 4, 5, 6, 21, 22")
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
                                .queryParam("budgetIds", "23")
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
                        .queryParam("budgetIds", "24, 7, 8, 9, 25").build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.budgetType").exists();

    }

}
