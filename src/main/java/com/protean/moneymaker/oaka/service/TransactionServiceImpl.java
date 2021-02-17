package com.protean.moneymaker.oaka.service;

import com.protean.moneymaker.oaka.dto.BudgetSubCategoryDto;
import com.protean.moneymaker.oaka.dto.BudgetSummary;
import com.protean.moneymaker.oaka.dto.TransactionBudgetSummary;
import com.protean.moneymaker.oaka.dto.TransactionDto;
import com.protean.moneymaker.oaka.model.Transaction;
import com.protean.moneymaker.oaka.model.TransactionCategory;
import com.protean.moneymaker.oaka.repository.TransactionRepository;
import com.protean.moneymaker.oaka.repository.TransactionSubCategoryRepository;
import com.protean.moneymaker.oaka.repository.TransactionTypeRepository;
import com.protean.moneymaker.oaka.util.TransactionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionSubCategoryRepository transactionSubCategoryRepository;
    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
            TransactionSubCategoryRepository transactionSubCategoryRepository,
            TransactionTypeRepository transactionTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionSubCategoryRepository = transactionSubCategoryRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    public List<Transaction> saveAllTransactions(Set<TransactionDto> transactions) {

        return transactionRepository.saveAll(TransactionUtil.mapDtosToEntities(transactions));
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();
    }

    @Override
    public Set<Transaction> getAllTransactionsOrdered() {
        return transactionRepository.findAllByOrderByDateDesc();
    }

    @Override
    @Transactional
    public Set<TransactionDto> getAllTransactionDtos() {

        Set<Transaction> transactions = getAllTransactionsOrdered();
        ModelMapper modelMapper = new ModelMapper();

//        PropertyMap<Transaction, TransactionDto> propertyMap = new PropertyMap<Transaction, TransactionDto>() {
//            @Override
//            protected void configure() {
//                map().getTransactionCategory().setId(source.getRecurringTransaction().getName());
//            }
//        };
//
//        modelMapper.addMappings(propertyMap);

        Set<TransactionDto> dtos = new LinkedHashSet<>();
        for (Transaction t : transactions) {
            TransactionDto dto = modelMapper.map(t, TransactionDto.class);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<BudgetSubCategoryDto> getAllTransactionCategories() {

//        List<BudgetSubCategory> categories = new ArrayList<>();
//        transactionCategoryRepository.findAll().forEach(categories::add);

        return null;
    }

    @Override
    public List<TransactionCategory> getAllTransactionSubCategories() {
        return transactionSubCategoryRepository.findAll();
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public void deleteTransactions(List<Transaction> transactions) {
        transactionRepository.deleteAll(transactions);
    }

    @Override
    public Set<TransactionBudgetSummary> getTransactionBudgetSummaryForAllTransactionTypes(int year, int month, List<BudgetSummary> budgetSummaries) {

        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than zero, but was <" + year + ">");
        }
        if (month <= 0 || month > 12) {
            throw new IllegalArgumentException("Valid month between 1 and 12 must be provided, but was <" + month + ">");
        }

        Set<TransactionBudgetSummary> summaries = new HashSet<>();

        for (BudgetSummary budget : budgetSummaries) {
            // FIXME: need to externalize some of this
//            TransactionBudgetSummary summary = transactionRepository.getBudgetSummaries(year, month, budget.getCategoryId(), budget.getTransactionTypeId()).orElse(
//                    new TransactionBudgetSummary(budget.getTransactionType(), budget.getCategory(), month, null, year, budget.getPlanned(), BigDecimal.ZERO, false)
//            );
//
//            if (summary.getPlanned().doubleValue() > 0 || summary.getActual().doubleValue() > 0) {
//                summaries.add(summary);
//            }

        }

        return summaries;
    }
}
