package com.factotum.oaka.service;

import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.BudgetSummary;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.model.TransactionCategory;
import com.factotum.oaka.repository.TransactionRepository;
import com.factotum.oaka.repository.TransactionSubCategoryRepository;
import com.factotum.oaka.util.TransactionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionSubCategoryRepository transactionSubCategoryRepository;
    private final AccountService accountService;
    private final BudgetService budgetService;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            TransactionSubCategoryRepository transactionSubCategoryRepository,
            AccountService accountService, BudgetService budgetService) {
        this.transactionRepository = transactionRepository;
        this.transactionSubCategoryRepository = transactionSubCategoryRepository;
        this.accountService = accountService;
        this.budgetService = budgetService;
    }

    @Override
    public Flux<Transaction> saveAllTransactions(Set<TransactionDto> transactions) {

        return transactionRepository.saveAll(TransactionUtil.mapDtosToEntities(transactions));
    }

    @Override
    public Mono<Transaction> saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    public Flux<TransactionDto> getAllTransactionDtos() {

        Map<Long, ShortAccountDto> accountDtoMap = new HashMap<>();
        Map<Long, BudgetDto> budgetMap = new HashMap<>();

        return transactionRepository.findAllByOrderByDateDesc()
                .map(t -> new ModelMapper().map(t, TransactionDto.class))
                .doOnNext(t ->
                        t.setAccount(
                                accountDtoMap.computeIfAbsent(
                                        t.getAccount().getId(),
                                        accountService::getAccountById))
                ).doOnNext(t -> {
                    if (t.getBudget() != null && t.getBudget().getId() != null) {
                        budgetMap.computeIfAbsent(t.getBudget().getId(), budgetService::getBudgetById);
                    }
                });
//        ModelMapper modelMapper = new ModelMapper();
//
//        Set<TransactionDto> dtos = new LinkedHashSet<>();
//        Map<Long, ShortAccountDto> accountDtoMap = new HashMap<>();
//        Map<Long, BudgetDto> budgetMap = new HashMap<>();
//        for (Transaction t : transactions) {
//
//            TransactionDto dto = modelMapper.map(t, TransactionDto.class);
//
//            dto.setAccount(
//                    accountDtoMap.computeIfAbsent(t.getAccountId(), accountService::getAccountById)
//            );
//
//            if (t.getBudgetId() != null) {
//                dto.setBudget(
//                        budgetMap.computeIfAbsent(t.getBudgetId(), budgetService::getBudgetById)
//                );
//            }
//
//            dtos.add(dto);
//        }
//        return dtos;
    }

    @Override
    public Flux<TransactionCategory> getAllTransactionSubCategories() {
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
    public List<TransactionBudgetSummary> getTransactionBudgetSummaryForAllTransactionTypes(int year, int month, List<BudgetSummary> budgetSummaries) {

        if (year <= 0) {
            throw new IllegalArgumentException("Year must be greater than zero, but was <" + year + ">");
        }
        if (month <= 0 || month > 12) {
            throw new IllegalArgumentException("Valid month between 1 and 12 must be provided, but was <" + month + ">");
        }
        if (budgetSummaries == null) {
            throw new IllegalArgumentException("Budget summaries must not be null");
        }

        List<TransactionBudgetSummary> summaries = new ArrayList<>();

        for (BudgetSummary budget : budgetSummaries) {
            TransactionBudgetSummary summary = transactionRepository.getBudgetSummaries(
                    year, month, budget.getBudgetIds(), budget.getTransactionTypeId())
                    .defaultIfEmpty(
                            new TransactionBudgetSummary(
                                    budget.getTransactionType(), budget.getCategory(), month, null, year, budget.getPlanned(), BigDecimal.ZERO, false)
                    ).block();

            assertThat(summary, is(not(nullValue())));
            if (summary.getPlanned().doubleValue() > 0 || summary.getActual().doubleValue() > 0) {
                summaries.add(summary);
            }

        }

        return summaries;
    }
}
