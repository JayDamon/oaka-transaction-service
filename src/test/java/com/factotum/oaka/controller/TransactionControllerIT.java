package com.factotum.oaka.controller;

import com.factotum.oaka.IntegrationTest;
import com.factotum.oaka.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@IntegrationTest
class TransactionControllerIT {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private TransactionRepository transactionRepository;

    private static final String URI = "/v1/transactions";

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void getAllTransactions() {

        webTestClient.get().uri(URI)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
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

//    @Test
//    void saveNewTransactions_GivenValidTransactionsProvided_ThenSaveTransactionsAndReturnOk() throws Exception {
//
//        ShortAccountDto accountDto = new ShortAccountDto();
//        accountDto.setId(1L);
//
//        BudgetDto budget = new BudgetDto();
//        budget.setId(1L);
//
//        TransactionDto t1 = new TransactionDto();
//        t1.setAccount(accountDto);
//        t1.setAmount(BigDecimal.valueOf(451.21));
//        t1.setDescription("Description 1");
//        t1.setDate(ZonedDateTime.now());
//        t1.setBudget(budget);
//
//        TransactionDto t2 = new TransactionDto();
//        t2.setAccount(accountDto);
//        t2.setAmount(BigDecimal.valueOf(56.22));
//        t2.setDescription("Description 564");
//        t2.setDate(ZonedDateTime.now().minusDays(10));
//        t2.setBudget(budget);
//
//        MvcResult result = mockMvc.perform(
//                post(URI)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(this.objectMapper.writeValueAsString(Arrays.asList(t1, t2))))
//                .andDo(print())
//                .status().isOk())
//                .jsonPath("$.[*]", hasSize(2)))
//                .andReturn();
//        MockHttpServletResponse response = result.getResponse();
//        JsonNode json = this.objectMapper.readTree(response.getContentAsString());
//        assertThat(json.isArray(), is(true));
//
//        int itemsToCount = 0;
//
//        for (JsonNode n : json) {
//            TransactionDto tran = this.objectMapper.readValue(n.toString(), TransactionDto.class);
//            assertThat(this.transactionRepository.existsById(tran.getId()), is(true));
//            itemsToCount++;
//        }
//
//        assertThat(itemsToCount, is(equalTo(json.size())));
//
//    }

    @Test
    void getTransactionTotal_GiveTransactionsExistForBudgets_ThenReturnTotalAmount() {

        webTestClient
                .get()
                .uri(URI + "/total")
                .attribute("year", "2017")
                .attribute("month", "1")
                .attribute("transactionTypeId", "2")
                .attribute("budgetIds", "10, 11, 12, 13, 14, 15, 26, 16, 17, 18, 27, 19, 28, 29, 30")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.total").exists()
                .jsonPath("$.transactionType").exists();

    }

    @Test
    void getTransactionTotal_GiveTransactionsExistForBudgetsTwenty_ThenReturnTotalAmount() {

        webTestClient
                .get()
                .uri(URI + "/total")
                .attribute("year", "2017")
                .attribute("month", "1")
                .attribute("transactionTypeId", "1")
                .attribute("budgetIds", "20")
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
                .uri(URI + "/total")
                .attribute("year", "2017")
                .attribute("month", "1")
                .attribute("transactionTypeId", "2")
                .attribute("budgetIds", "1, 2, 3, 4, 5, 6, 21, 22")
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
                .uri(URI + "/total")
                .attribute("year", "2017")
                .attribute("month", "1")
                .attribute("transactionTypeId", "1")
                .attribute("budgetIds", "23")
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
