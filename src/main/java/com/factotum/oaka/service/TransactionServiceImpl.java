package com.factotum.oaka.service;

import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final BudgetService budgetService;

    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountService accountService,
            BudgetService budgetService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.budgetService = budgetService;
    }

    @Override
    @Transactional
    public Flux<TransactionDto> getAllTransactionDtos(Jwt jwt) {

        Flux<ShortAccountDto> accounts = accountService.getAccounts(jwt);
        Flux<BudgetDto> budgetDtoFlux = budgetService.getBudgets(jwt);

        Flux<TransactionDto> transactions = transactionRepository.findAllByOrderByDateDesc(jwt.getClaimAsString("sub"));

        Flux<TransactionDto> withAccounts = accounts.collectMap(ShortAccountDto::getId, a -> a)
                .flatMapMany(accountDtos -> transactions.handle((t, sink) -> {
                    if (t.getAccount() != null) {
                        Long accountId = t.getAccount().getId();
                        if (accountDtos.containsKey(accountId)) {
                            ShortAccountDto accountDto = accountDtos.get(accountId);
                            t.setAccount(accountDto);
                        }
                    }
                    sink.next(t);
                }));

        return budgetDtoFlux.collectMap(BudgetDto::getId, b -> b)
                .flatMapMany(budgetDtos -> withAccounts.handle((t, sink) -> {
                    BudgetDto originalBudgetDto = t.getBudget();
                    if (originalBudgetDto != null && originalBudgetDto.getId() != null) {
                        Long budgetId = t.getBudget().getId();
                        if (budgetDtos.containsKey(budgetId)) {
                            BudgetDto budgetDto = budgetDtos.get(budgetId);
                            t.setBudget(budgetDto);
                        }
                    } else {
                        t.setBudget(new BudgetDto());
                    }
                    sink.next(t);
                }));
    }

    @Override
    public Mono<Transaction> updateTransaction(Jwt jwt, TransactionDto updatedTransaction) {

        if (jwt == null) {
            throw new IllegalArgumentException("Jwt must not be null");
        }

        if (updatedTransaction == null) throw new IllegalArgumentException("Transaction must not be null");

        return getTransactionById(updatedTransaction.getId(), jwt.getClaimAsString("sub"))
                .flatMap(t -> addTransactionUpdatesToEntity(updatedTransaction, t))
                .flatMap(transactionRepository::save);
    }

    private Mono<Transaction> addTransactionUpdatesToEntity(TransactionDto transaction, Transaction t) {

        if (transaction.getAmount() != null) t.setAmount(transaction.getAmount());
        if (transaction.getDescription() != null) t.setDescription(transaction.getDescription());
        if (transaction.getDate() != null) t.setDate(transaction.getDate());
        if (transaction.getAccount() != null) t.setAccountId(transaction.getAccount().getId());
        if (transaction.getBudget() != null) t.setBudgetId(transaction.getBudget().getId());
        if (transaction.getTransactionCategory() != null) t.setTransactionCategory(transaction.getTransactionCategory().getId());

        return Mono.just(t);
    }

    private Mono<Transaction> getTransactionById(long transactionId, String userId) {

        Mono<Transaction> fallback = Mono.error(
                new NoSuchElementException(
                        String.format(
                                "Transaction with id %s was not found",
                                transactionId
                        )));

        return transactionRepository
                .findByIdAndTenantId(transactionId, userId)
                .switchIfEmpty(fallback);
    }

}
