package com.factotum.oaka.service;

import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.http.AccountService;
import com.factotum.oaka.http.BudgetService;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.Comparator;

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
    public Flux<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    public Flux<TransactionDto> getAllTransactionDtos() {

        Flux<ShortAccountDto> accounts = accountService.getAccounts();
        Flux<BudgetDto> budgetDtoFlux = budgetService.getBudgets();
        Flux<TransactionDto> transactions = transactionRepository.findAllByOrderByDateDesc();

        Flux<TransactionDto> withAccounts = accounts.collectMap(ShortAccountDto::getId, a -> a)
                .flatMapMany(accountDtos -> transactions.handle((t, sink) -> {
                    Long accountId = t.getAccount().getId();
                    if (accountDtos.containsKey(accountId)) {
                        ShortAccountDto accountDto = accountDtos.get(accountId);
                        t.setAccount(accountDto);
                        sink.next(t);
                    }
                }));
        Flux<TransactionDto> withBudgets = budgetDtoFlux.collectMap(BudgetDto::getId, b -> b)
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
        return withBudgets.sort(Comparator.comparing(TransactionDto::getDate).reversed());
    }

}
