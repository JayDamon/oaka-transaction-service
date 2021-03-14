package com.factotum.oaka.service;

import com.factotum.oaka.dto.BudgetCategoryDto;
import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.BudgetSubCategoryDto;
import com.factotum.oaka.dto.BudgetSummary;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.dto.TransactionCategoryDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.model.BudgetSubCategory;
import com.factotum.oaka.model.Occurrence;
import com.factotum.oaka.model.RecurringTransaction;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.model.TransactionCategory;
import com.factotum.oaka.model.TransactionType;
import com.factotum.oaka.repository.TransactionRepository;
import com.factotum.oaka.repository.TransactionSubCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplUT {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionSubCategoryRepository transactionSubCategoryRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private BudgetService budgetService;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl(
                transactionRepository, transactionSubCategoryRepository, accountService, budgetService);
    }

    @Test
    void getAllTransactionDtos_GivenCompleteTransactionsFound_ThenMapCompleteDto() {

        // Arrange
        TransactionType transactionType = new TransactionType(9, "TransactionTypeOne");

        BudgetSubCategory budgetSubCategory = new BudgetSubCategory(10L, "BudgetSubCategoryOne");
        TransactionCategory transactionCategory = new TransactionCategory(11L, "TransactionCategoryOne", budgetSubCategory.getId());

        Occurrence occurrence = new Occurrence(12, "OccurrenceOne");

        RecurringTransaction recurringTransaction = new RecurringTransaction(
                13L, "RecurringTransactionName", 3L, 10, transactionCategory.getId(),
                7, 2, occurrence.getId(), transactionType.getId(), LocalDateTime.now(),
                LocalDateTime.now().plusHours(25), BigDecimal.valueOf(34.66));
        ShortAccountDto accountDto = new ShortAccountDto(3L, "Account 1");

        Transaction transaction = new Transaction(14L, accountDto.getId(), 8L, transactionCategory.getId(),
                transactionType.getId(), recurringTransaction.getId(), LocalDateTime.now(),
                "TransactionDescriptionOne", BigDecimal.valueOf(44.78));

        ModelMapper mapper = new ModelMapper();

        TransactionDto transactionDto = mapper.map(transaction, TransactionDto.class);
        TransactionCategoryDto transactionCategoryDto = mapper.map(transactionCategory, TransactionCategoryDto.class);
        BudgetSubCategoryDto budgetSubCategoryDto = mapper.map(budgetSubCategory, BudgetSubCategoryDto.class);
        transactionCategoryDto.setBudgetSubCategory(budgetSubCategoryDto);
        transactionDto.setTransactionCategory(transactionCategoryDto);
        when(transactionRepository.findAllByOrderByDateDesc()).thenReturn(Flux.just(transactionDto));

        when(accountService.getAccountById(eq(accountDto.getId()))).thenReturn(Mono.just(accountDto));

        BudgetCategoryDto budgetCategory = new BudgetCategoryDto();
        budgetCategory.setId(6);
        budgetCategory.setName("BudgetCategoryName");
        budgetCategory.setTypeName("BudgetCategoryType");
        BudgetDto budget = new BudgetDto();
        budget.setId(8L);
        budget.setName("BudgetItemNameOne");
        budget.setBudgetCategory(budgetCategory);
        when(budgetService.getBudgetById(anyLong())).thenReturn(Mono.just(budget));

        // Act
        TransactionDto dto = transactionService.getAllTransactionDtos().blockFirst();

        // Assert
        assertThat(dto, is(not(nullValue())));
        assertThat(dto.getId(), is(equalTo(14L)));
        assertThat(dto.getAmount(), is(equalTo(BigDecimal.valueOf(44.78))));
        assertThat(dto.getDescription(), is(equalTo("TransactionDescriptionOne")));
        assertThat(dto.getDate().getMonth(), is(equalTo(LocalDateTime.now().getMonth())));
        assertThat(dto.getAccount().getId(), is(equalTo(3L)));
        assertThat(dto.getAccount().getName(), is(equalTo("Account 1")));
        assertThat(dto.getBudget().getId(), is(equalTo(8L)));
        assertThat(dto.getBudget().getName(), is(equalTo("BudgetItemNameOne")));
        assertThat(dto.getBudget().getBudgetCategory().getId(), is(equalTo(6)));
        assertThat(dto.getBudget().getBudgetCategory().getTypeName(), is(equalTo("BudgetCategoryType")));
        assertThat(dto.getBudget().getBudgetCategory().getName(), is(equalTo("BudgetCategoryName")));
        assertThat(dto.getTransactionCategory().getId(), is(equalTo(11L)));
        assertThat(dto.getTransactionCategory().getName(), is(equalTo("TransactionCategoryOne")));
        assertThat(dto.getTransactionCategory().getBudgetSubCategory().getId(), is(equalTo(10L)));
        assertThat(dto.getTransactionCategory().getBudgetSubCategory().getName(), is(equalTo("BudgetSubCategoryOne")));
    }

    @Test
    void getTransactionBudgetSummaryForAllTransactionTypes_GivenTransactionsForBudgetExist_ThenReturnSummary() {

        // Arrange
        TransactionBudgetSummary summary = new TransactionBudgetSummary(
                "TestType", "TestCategory", 1, "January", 2017, BigDecimal.valueOf(50.02), BigDecimal.valueOf(40.30), false);

        when(transactionRepository.getBudgetSummaries(eq(2017), eq(1), any(), anyInt())).thenReturn(Mono.just(summary));

        BudgetSummary category = new BudgetSummary();
        category.setCategoryId(1);
        category.setTransactionTypeId(2);

        // Act
        List<TransactionBudgetSummary> summaries = transactionService
                .getTransactionBudgetSummaryForAllTransactionTypes(
                        2017, 1, Collections.singletonList(category));

        // Assert
        assertThat(summaries, is(not(nullValue())));

        assertThat(summaries.get(0), is(equalTo(summary)));

    }

    @Test
    void getTransactionBudgetSummaryForAllTransactionTypesForAllTransactionTypes_GivenBudgetsAreEmpty_ThenReturnEmptyCollection() {
        assertThat(transactionService.getTransactionBudgetSummaryForAllTransactionTypes(2017, 1, new ArrayList<>()), is(emptyIterable()));
    }

    @Test
    void getTransactionBudgetSummaryForAllTransactionTypesForAllTransactionTypes_GivenYearLessThanZero_ThenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.getTransactionBudgetSummaryForAllTransactionTypes(0, 1, new ArrayList<>()));
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.getTransactionBudgetSummaryForAllTransactionTypes(-500, 1, new ArrayList<>()));
    }

    @Test
    void getTransactionBudgetSummaryForAllTransactionTypesForAllTransactionTypes_GivenMonthLessThanZero_ThenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.getTransactionBudgetSummaryForAllTransactionTypes(1, 0, new ArrayList<>()));
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.getTransactionBudgetSummaryForAllTransactionTypes(1, -500, new ArrayList<>()));
    }

    @Test
    void getTransactionBudgetSummaryForAllTransactionTypesForAllTransactionTypes_GivenMonthGreaterThanTwelve_ThenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.getTransactionBudgetSummaryForAllTransactionTypes(1, 13, new ArrayList<>()));
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.getTransactionBudgetSummaryForAllTransactionTypes(1, 500, new ArrayList<>()));
    }

    @Test
    void getTransactionBudgetSummaryForAllTransactionTypesForAllTransactionTypes_GivenBudgetSummariesIsNull_ThenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.getTransactionBudgetSummaryForAllTransactionTypes(2017, 1, null));
    }

}
