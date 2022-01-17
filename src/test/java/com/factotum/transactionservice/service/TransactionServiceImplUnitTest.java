package com.factotum.transactionservice.service;

import com.factotum.transactionservice.dto.*;
import com.factotum.transactionservice.http.AccountService;
import com.factotum.transactionservice.http.BudgetService;
import com.factotum.transactionservice.model.Transaction;
import com.factotum.transactionservice.model.TransactionCategory;
import com.factotum.transactionservice.model.TransactionSubCategory;
import com.factotum.transactionservice.repository.TransactionRepository;
import com.factotum.transactionservice.sender.TransactionChangeSender;
import com.factotum.transactionservice.util.SecurityTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplUnitTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private BudgetService budgetService;

    @Mock
    private TransactionChangeSender transactionChangeSender;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl(
                transactionRepository, accountService, budgetService, transactionChangeSender);
    }

    // getAllTransactionDtos
    @Test
    void getAllTransactionDtos_GivenCompleteTransactionsFound_ThenMapCompleteDto() {

        // Arrange
        String tenantId = "test_tenant_id";

        TransactionSubCategory transactionSubCategory = new TransactionSubCategory(10L, "BudgetSubCategoryOne");
        TransactionCategory transactionCategory = new TransactionCategory(11L, "TransactionCategoryOne", transactionSubCategory.getId());

        ShortAccountDto accountDto = new ShortAccountDto(3L, "Account 1");

        Transaction transaction = new Transaction(14L, accountDto.getId(), 8L, transactionCategory.getId(),
                9, 13L, LocalDate.now(),
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

    @Test
    void getAllTransactionDtos_GivenTransactionAccountMissing_TheReturnTransactionWithNoAccount() {

        // Arrange
        String tenantId = "test_tenant_id";

        TransactionSubCategory transactionSubCategory = new TransactionSubCategory(10L, "BudgetSubCategoryOne");
        TransactionCategory transactionCategory = new TransactionCategory(11L, "TransactionCategoryOne", transactionSubCategory.getId());

        Transaction transaction = new Transaction(14L, null, 8L, transactionCategory.getId(),
                9, 13L, LocalDate.now(),
                "TransactionDescriptionOne", BigDecimal.valueOf(44.78), tenantId);

        ModelMapper mapper = new ModelMapper();

        TransactionDto transactionDto = mapper.map(transaction, TransactionDto.class);
        TransactionCategoryDto transactionCategoryDto = mapper.map(transactionCategory, TransactionCategoryDto.class);
        BudgetSubCategoryDto budgetSubCategoryDto = mapper.map(transactionSubCategory, BudgetSubCategoryDto.class);
        transactionCategoryDto.setBudgetSubCategory(budgetSubCategoryDto);
        transactionDto.setTransactionCategory(transactionCategoryDto);
        when(transactionRepository.findAllByOrderByDateDesc(eq(tenantId))).thenReturn(Flux.just(transactionDto));

        when(accountService.getAccounts(any())).thenReturn(Flux.empty());

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
        assertThat(dto.getAccount(), is(nullValue()));
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

    // updateTransaction
    @Test
    void updateTransaction_GivenAllFieldsChanged_ThenSaveChangedTransaction() {

        // Arrange
        BigDecimal amount = BigDecimal.valueOf(21.048);
        String description = "New Description";
        LocalDate date = LocalDate.now();
        ShortAccountDto accountDto = new ShortAccountDto();
        accountDto.setId(4L);
        BudgetDto budgetDto = new BudgetDto();
        budgetDto.setId(2L);
        TransactionCategoryDto transactionCategoryDto = new TransactionCategoryDto();
        transactionCategoryDto.setId(6L);

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(1L);
        transactionDto.setAmount(amount);
        transactionDto.setDescription(description);
        transactionDto.setDate(date);
        transactionDto.setAccount(accountDto);
        transactionDto.setBudget(budgetDto);
        transactionDto.setTransactionCategory(transactionCategoryDto);

        Transaction transaction = new Transaction();
        transaction.setId(1L);

        String userId = "TestUserId";

        when(this.transactionRepository.findByIdAndTenantId(eq(transactionDto.getId()), eq(userId)))
                .thenReturn(Mono.just(transaction));

        when(this.transactionRepository.save(any())).thenAnswer(i -> Mono.just(i.getArgument(0)));

        // Act
        Transaction updatedTransaction = this.transactionService.updateTransaction(SecurityTestUtil.getTestJwt(userId), transactionDto).block();

        // Assert
        assertThat(updatedTransaction, is(not(nullValue())));
        assertThat(updatedTransaction.getAmount(), is(equalTo(amount)));
        assertThat(updatedTransaction.getDescription(), is(equalTo(description)));
        assertThat(updatedTransaction.getDate(), is(equalTo(date)));
        assertThat(updatedTransaction.getAccountId(), is(equalTo(accountDto.getId())));
        assertThat(updatedTransaction.getBudgetId(), is(equalTo(budgetDto.getId())));
        assertThat(updatedTransaction.getTransactionCategory(), is(equalTo(transactionCategoryDto.getId())));

        verify(transactionChangeSender, times(1)).sendTransactionChangedMessage(eq(updatedTransaction));
    }

    @Test
    void updateTransaction_GivenTransactionDtoIsNull_ThenThrowIllegalArgumentException() {
        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> this.transactionService.updateTransaction(SecurityTestUtil.getTestJwt(), null).block());

        assertThat(throwable.getMessage(), is(equalTo("Transaction must not be null")));
    }

    @Test
    void updateTransaction_GivenJwtIsNull_ThenThrowIllegalArgumentException() {
        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> this.transactionService.updateTransaction(null, new TransactionDto()).block());

        assertThat(throwable.getMessage(), is(equalTo("Jwt must not be null")));
    }

    @Test
    void updateTransaction_GivenTransactionNotFound_ThenThrowNotFoundException() {

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(1L);

        when(this.transactionRepository.findByIdAndTenantId(eq(transactionDto.getId()), anyString()))
                .thenReturn(Mono.empty());

        Throwable throwable = assertThrows(NoSuchElementException.class,
                () -> this.transactionService.updateTransaction(SecurityTestUtil.getTestJwt(), transactionDto).block());

        assertThat(throwable.getMessage(), is(equalTo("Transaction with id 1 was not found")));
    }

}
