package com.factotum.oaka.service;

import com.factotum.oaka.dto.BudgetSubCategoryDto;
import com.factotum.oaka.dto.BudgetSummary;
import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.model.TransactionCategory;

import java.util.List;
import java.util.Set;

public interface TransactionService {

    List<Transaction> saveAllTransactions(Set<TransactionDto> transactions);

    Transaction saveTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    Set<Transaction> getAllTransactionsOrdered();

    Set<TransactionDto> getAllTransactionDtos();

    List<BudgetSubCategoryDto> getAllTransactionCategories(); // TODO use transaction category controller

    List<TransactionCategory> getAllTransactionSubCategories();

    void deleteTransaction(Transaction id);

    void deleteTransactions(List<Transaction> ids);

    Set<TransactionBudgetSummary> getTransactionBudgetSummaryForAllTransactionTypes(int year, int month, List<BudgetSummary> budgetCategoryTypeId);

}
