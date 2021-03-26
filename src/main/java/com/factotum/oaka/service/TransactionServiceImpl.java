package com.factotum.oaka.service;

import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.repository.TransactionRepository;
import com.factotum.oaka.repository.TransactionSubCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
    public Flux<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    public Flux<TransactionDto> getAllTransactionDtos() {

        Map<Long, Mono<BudgetDto>> budgetMap = new ConcurrentHashMap<>();
        Map<Long, Mono<ShortAccountDto>> accountMap = new ConcurrentHashMap<>();

        return transactionRepository.findAllByOrderByDateDesc()
                .flatMap(t ->
                        getAccount(t.getAccount(), accountMap).map(a -> addAccount(a, t)))
                .flatMap(t ->
                        getBudget(t.getBudget(), budgetMap).map(b -> addBudget(b, t)))
                .sort(Comparator.comparing(TransactionDto::getDate).reversed());
    }

    private TransactionDto addBudget(BudgetDto budget, TransactionDto transaction) {
        if (budget.getId() != null) {
            transaction.setBudget(budget);
        }
        return transaction;
    }

    private TransactionDto addAccount(ShortAccountDto account, TransactionDto transactionDto) {
        transactionDto.setAccount(account);
        return transactionDto;
    }

    private Mono<ShortAccountDto> getAccount(
            ShortAccountDto account, Map<Long, Mono<ShortAccountDto>> accounts) {
        return accounts.computeIfAbsent(account.getId(), accountService::getAccountById);
    }

    private Mono<BudgetDto> getBudget(BudgetDto budgetDto, Map<Long, Mono<BudgetDto>> budgets) {
        if (budgetDto != null && budgetDto.getId() != null) {
            return budgets.computeIfAbsent(budgetDto.getId(), budgetService::getBudgetById);
        } else {
            return Mono.just(new BudgetDto());
        }
    }

}
