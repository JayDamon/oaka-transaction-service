package com.factotum.oaka.service;

import com.factotum.oaka.dto.BudgetCategoryDto;
import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.BudgetSubCategoryDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionCategoryDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.model.Occurrence;
import com.factotum.oaka.model.RecurringTransaction;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.model.TransactionCategory;
import com.factotum.oaka.model.TransactionSubCategory;
import com.factotum.oaka.model.TransactionType;
import com.factotum.oaka.repository.TransactionRepository;
import com.factotum.oaka.util.SecurityTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplUT {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private BudgetService budgetService;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl(
                transactionRepository, accountService, budgetService);
    }

    @Test
    void getAllTransactionDtos_GivenCompleteTransactionsFound_ThenMapCompleteDto() {

        // Arrange
        String tenantId = "test_tenant_id";

        TransactionType transactionType = new TransactionType(9, "TransactionTypeOne");

        TransactionSubCategory transactionSubCategory = new TransactionSubCategory(10L, "BudgetSubCategoryOne");
        TransactionCategory transactionCategory = new TransactionCategory(11L, "TransactionCategoryOne", transactionSubCategory.getId());

        Occurrence occurrence = new Occurrence(12, "OccurrenceOne");

        RecurringTransaction recurringTransaction = new RecurringTransaction(
                13L, "RecurringTransactionName", 3L, 10, transactionCategory.getId(),
                7, 2, occurrence.getId(), transactionType.getId(), LocalDateTime.now(),
                LocalDateTime.now().plusHours(25), BigDecimal.valueOf(34.66), tenantId);
        ShortAccountDto accountDto = new ShortAccountDto(3L, "Account 1");

        Transaction transaction = new Transaction(14L, accountDto.getId(), 8L, transactionCategory.getId(),
                transactionType.getId(), recurringTransaction.getId(), LocalDate.now(),
                "TransactionDescriptionOne", BigDecimal.valueOf(44.78), tenantId);

        ModelMapper mapper = new ModelMapper();

        TransactionDto transactionDto = mapper.map(transaction, TransactionDto.class);
        TransactionCategoryDto transactionCategoryDto = mapper.map(transactionCategory, TransactionCategoryDto.class);
        BudgetSubCategoryDto budgetSubCategoryDto = mapper.map(transactionSubCategory, BudgetSubCategoryDto.class);
        transactionCategoryDto.setBudgetSubCategory(budgetSubCategoryDto);
        transactionDto.setTransactionCategory(transactionCategoryDto);
        when(transactionRepository.findAllByOrderByDateDesc(eq(tenantId))).thenReturn(Flux.just(transactionDto));

        when(accountService.getAccounts(any())).thenReturn(Flux.just(accountDto));

        BudgetCategoryDto budgetCategory = new BudgetCategoryDto();
        budgetCategory.setId(6);
        budgetCategory.setName("BudgetCategoryName");
        budgetCategory.setTypeName("BudgetCategoryType");
        BudgetDto budget = new BudgetDto();
        budget.setId(8L);
        budget.setName("BudgetItemNameOne");
        budget.setBudgetCategory(budgetCategory);
        when(budgetService.getBudgets(any())).thenReturn(Flux.just(budget));

        // Act
        TransactionDto dto = transactionService.getAllTransactionDtos(SecurityTestUtil.getTestJwt(tenantId)).blockFirst();

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

}
