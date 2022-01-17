package com.factotum.transactionservice.controller;

import com.factotum.transactionservice.dto.TransactionCategoryDto;
import com.factotum.transactionservice.dto.TransactionDto;
import com.factotum.transactionservice.dto.TransactionTypeTotal;
import com.factotum.transactionservice.enumeration.BudgetType;
import com.factotum.transactionservice.model.Transaction;
import com.factotum.transactionservice.repository.TransactionCategoryRepository;
import com.factotum.transactionservice.repository.TransactionRepository;
import com.factotum.transactionservice.sender.TransactionChangeSender;
import com.factotum.transactionservice.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/v1/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;
    private final TransactionChangeSender transactionChangeSender;

    public TransactionController(
            TransactionService transactionService,
            TransactionRepository transactionRepository,
            TransactionCategoryRepository transactionCategoryRepository,
            TransactionChangeSender transactionChangeSender) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.transactionCategoryRepository = transactionCategoryRepository;
        this.transactionChangeSender = transactionChangeSender;
    }

    @GetMapping("")
    public Flux<TransactionDto> getAllTransactions(JwtAuthenticationToken jwt) {
        return transactionService.getAllTransactionDtos(jwt.getToken());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Flux<TransactionDto> createTransactions(@Valid @RequestBody Flux<TransactionDto> transactions) {
        return transactions
                .map(t -> new ModelMapper().map(t, Transaction.class))
                .flatMap(transactionRepository::save)
                .doOnNext(transactionChangeSender::sendTransactionChangedMessage)
                .map(t -> new ModelMapper().map(t, TransactionDto.class));
    }

    @PatchMapping("/{id}")
    Mono<TransactionDto> updateTransaction(
            JwtAuthenticationToken jwt,
            @PathVariable(name = "id") long id,
            @RequestBody Mono<TransactionDto> transaction) {

        return transaction
                .map(t -> {

                    if (t.getId() == null)
                        throw new IllegalArgumentException("Body must contain a valid transaction id");

                    if (t.getId() != id)
                        throw new IllegalArgumentException("Transaction id must match the body, but does not");

                    return t;
                })
                .flatMap(t -> this.transactionService.updateTransaction(jwt.getToken(), t))
                .map(t -> new ModelMapper().map(t, TransactionDto.class));
    }

    @GetMapping("/categories")
    public Flux<TransactionCategoryDto> getTransactionCategories() {
        return transactionCategoryRepository.queryAll();
    }

    @GetMapping("/total")
    public Mono<TransactionTypeTotal> getTransactionTotal(
            JwtAuthenticationToken jwt,
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            @RequestParam(name = "budgetType") BudgetType budgetType,
            @RequestParam(name = "budgetIds") Set<Long> budgetIds) {

        if (BudgetType.INCOME.equals(budgetType)) {
            return this.transactionRepository.getIncomeTransactionSummary(month, year, budgetIds, jwt.getToken().getClaimAsString("sub"))
                    .map(sum -> new TransactionTypeTotal(budgetType, sum.getActual()))
                    .switchIfEmpty(Mono.just(new TransactionTypeTotal(budgetType, BigDecimal.ZERO)));
        } else {
            return this.transactionRepository.getExpenseTransactionSummary(month, year, budgetIds, jwt.getToken().getClaimAsString("sub"))
                    .map(sum -> new TransactionTypeTotal(budgetType, sum.getActual()))
                    .switchIfEmpty(Mono.just(new TransactionTypeTotal(budgetType, BigDecimal.ZERO)));
        }

    }
}
