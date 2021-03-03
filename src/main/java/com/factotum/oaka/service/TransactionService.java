package com.factotum.oaka.service;

import com.factotum.oaka.dto.BudgetSummary;
import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.model.TransactionCategory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface TransactionService {

    Flux<Transaction> saveAllTransactions(Set<TransactionDto> transactions);

    Mono<Transaction> saveTransaction(Transaction transaction);

    Flux<Transaction> getAllTransactions();

    Flux<TransactionDto> getAllTransactionDtos();

    Flux<TransactionCategory> getAllTransactionSubCategories();

    void deleteTransaction(Transaction id);

    void deleteTransactions(List<Transaction> ids);

    List<TransactionBudgetSummary> getTransactionBudgetSummaryForAllTransactionTypes(
            int year, int month, List<BudgetSummary> budgetCategoryTypeId);

}
