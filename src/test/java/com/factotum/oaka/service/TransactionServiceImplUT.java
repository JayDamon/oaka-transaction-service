package com.factotum.oaka.service;

import com.factotum.oaka.dto.BudgetCategoryDto;
import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.BudgetSummary;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionBudgetSummary;
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

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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

        BudgetSubCategory budgetSubCategory = new BudgetSubCategory(10L, "BudgetSubCategoryOne", new HashSet<>());
        TransactionCategory transactionCategory = new TransactionCategory(11L, "TransactionCategoryOne", budgetSubCategory);

        Occurrence occurrence = new Occurrence(12, "OccurrenceOne");

        RecurringTransaction recurringTransaction = new RecurringTransaction(
                13L, "RecurringTransactionName", 3L, 10, transactionCategory,
                7, 2, occurrence, transactionType, ZonedDateTime.now(),
                ZonedDateTime.now().plusHours(25), BigDecimal.valueOf(34.66));
        ShortAccountDto accountDto = new ShortAccountDto(3L, "Account 1");

        Transaction transaction = new Transaction(14L, accountDto.getId(), 8L, transactionCategory,
                transactionType, recurringTransaction, ZonedDateTime.now(),
                "TransactionDescriptionOne", BigDecimal.valueOf(44.78));

        when(transactionRepository.findAllByOrderByDateDesc()).thenReturn(new HashSet<>(Collections.singletonList(transaction)));

        when(accountService.getAccountById(eq(accountDto.getId()))).thenReturn(accountDto);

        BudgetCategoryDto budgetCategory = new BudgetCategoryDto();
        budgetCategory.setId(6);
        budgetCategory.setName("BudgetCategoryName");
        budgetCategory.setTypeName("BudgetCategoryType");
        BudgetDto budget = new BudgetDto();
        budget.setId(8L);
        budget.setName("BudgetItemNameOne");
        budget.setBudgetCategory(budgetCategory);
        when(budgetService.getBudgetById(anyLong())).thenReturn(budget);

        // Act
        Set<TransactionDto> transactions = transactionService.getAllTransactionDtos();

        // Assert
        assertThat(transactions, hasSize(1));
        int transactionsChecked = 0;
        for (TransactionDto dto : transactions) {
            assertThat(dto.getId(), is(equalTo(14L)));
            assertThat(dto.getAmount(), is(equalTo(BigDecimal.valueOf(44.78))));
            assertThat(dto.getDescription(), is(equalTo("TransactionDescriptionOne")));
            assertThat(dto.getDate().getMonth(), is(equalTo(ZonedDateTime.now().getMonth())));
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

            transactionsChecked++;
        }

        assertThat(transactionsChecked, is(equalTo(1)));
    }

    @Test
    void getTransactionBudgetSummaryForAllTransactionTypes_GivenTransactionsForBudgetExist_ThenReturnSummary() {

        // Arrange
        TransactionBudgetSummary summary = new TransactionBudgetSummary(
                "TestType", "TestCategory", 1, "January", 2017, BigDecimal.valueOf(50.02), BigDecimal.valueOf(40.30), false);

        when(transactionRepository.getBudgetSummaries(eq(2017), eq(1), any(), anyInt())).thenReturn(Optional.of(summary));

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
