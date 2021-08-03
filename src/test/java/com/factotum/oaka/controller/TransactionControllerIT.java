package com.factotum.oaka.controller;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.dto.BudgetCategoryDto;
import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.mockito.Mockito.when;

@IntegrationTest
@WithMockUser
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
        when(accountService.getAccounts()).thenReturn(Flux.fromIterable(shortAccountDtos));

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
        when(budgetService.getBudgets()).thenReturn(Flux.fromIterable(budgetDtos));
    }

    @Test
    void getAllTransactions() {

        webTestClient.get().uri(URI)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$[0].id").exists()
                .jsonPath("$[0].amount").exists()
                .jsonPath("$[0].description").exists()
                .jsonPath("$[0].date").exists()
                .jsonPath("$[0].category").exists()
                .jsonPath("$[0].category.id").exists()
                .jsonPath("$[0].category.name").exists()
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

    @Test
    void createNewTransactions_GivenSingleTransactionProvided_ThenCreateTransaction() {

        ShortAccountDto shortAccountDto = new ShortAccountDto();
        shortAccountDto.setId(3L);

        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(5L);

        LocalDateTime tnDate = LocalDateTime.of(2021, 2, 3, 1, 1);

        TransactionDto tn = new TransactionDto();
        tn.setAccount(shortAccountDto);
        tn.setBudget(budgetDto);
        tn.setDate(tnDate);
        tn.setDescription("Transaction Description");
        tn.setAmount(BigDecimal.valueOf(22.3));

        webTestClient
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
    void getTransactionCategories_GivenCategoriesExist_ThenReturnAllCategories() {

        webTestClient.get().uri(URI + "/categories")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$[0].id").exists()
                .jsonPath("$[0].name").exists()
                .jsonPath("$[0].subCategory").exists()
                .jsonPath("$[0].subCategory.id").exists()
                .jsonPath("$[0].subCategory.name").exists();

    }

    @Test
    void getTransactionTotal_GiveTransactionsExistForBudgets_ThenReturnTotalAmount() {

        webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(URI + "/total")
                                .queryParam("year", "2017")
                                .queryParam("month", "1")
                                .queryParam("transactionTypeId", "2")
                                .queryParam("budgetIds", "10, 11, 12, 13, 14, 15, 26, 16, 17, 18, 27, 19, 28, 29, 30")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.total").exists()
                .jsonPath("$.transactionType").exists();

    }

    @Test
    void getTransactionTotal_GiveTransactionsExistForBudgetsTwenty_ThenReturnTotalAmount() {

        webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(URI + "/total")
                                .queryParam("year", "2017")
                                .queryParam("month", "1")
                                .queryParam("transactionTypeId", "1")
                                .queryParam("budgetIds", "20")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.transactionType").exists();

    }

    @Test
    void getTransactionTotal_GiveTransactionsExistForBudgetsThree_ThenReturnTotalAmount() {

        webTestClient.
                get()
                .uri(uriBuilder ->
                        uriBuilder.path(URI + "/total")
                                .queryParam("year", "2017")
                                .queryParam("month", "1")
                                .queryParam("transactionTypeId", "2")
                                .queryParam("budgetIds", "1, 2, 3, 4, 5, 6, 21, 22")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.transactionType").exists();

    }

    @Test
    void getTransactionTotal_GiveNoTransactionsExist_ThenReturnTotalAmount() {

        webTestClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(URI + "/total")
                                .queryParam("year", "2017")
                                .queryParam("month", "1")
                                .queryParam("transactionTypeId", "1")
                                .queryParam("budgetIds", "23")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.transactionType").exists();

    }

    @Test
    void getTransactionTotal_GiveTransactionsExistForBudgetsSix_ThenReturnTotalAmount() {

        webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(URI + "/total")
                        .queryParam("year", "2017")
                        .queryParam("month", "1")
                        .queryParam("transactionTypeId", "2")
                        .queryParam("budgetIds", "24, 7, 8, 9, 25").build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.transactionType").exists();

    }

}
