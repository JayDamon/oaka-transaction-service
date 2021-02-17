package com.protean.moneymaker.oaka.service;

import com.protean.moneymaker.oaka.dto.BudgetSubCategoryDto;
import com.protean.moneymaker.oaka.dto.BudgetSummary;
import com.protean.moneymaker.oaka.dto.TransactionBudgetSummary;
import com.protean.moneymaker.oaka.dto.TransactionDto;
import com.protean.moneymaker.oaka.model.Transaction;
import com.protean.moneymaker.oaka.model.TransactionCategory;

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
