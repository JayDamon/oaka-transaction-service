package com.factotum.oaka.controller;

import com.factotum.oaka.dto.TransactionCategoryDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.dto.TransactionTypeTotal;
import com.factotum.oaka.model.Transaction;
import com.factotum.oaka.repository.TransactionCategoryRepository;
import com.factotum.oaka.repository.TransactionRepository;
import com.factotum.oaka.repository.TransactionTypeRepository;
import com.factotum.oaka.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/v1/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;

    public TransactionController(
            TransactionService transactionService,
            TransactionRepository transactionRepository,
            TransactionTypeRepository transactionTypeRepository,
            TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    @GetMapping("")
    public Flux<TransactionDto> getAllTransactions() {
        return transactionService.getAllTransactionDtos();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Flux<TransactionDto> createTransactions(@RequestBody Flux<TransactionDto> transactions) {
        log.info("Saving transactions");
        return transactions
                .map(t -> new ModelMapper().map(t, Transaction.class))
                .flatMap(transactionRepository::save)
                .map(t -> new ModelMapper().map(t, TransactionDto.class));
    }

    @GetMapping("/categories")
    public Flux<TransactionCategoryDto> getTransactionCategories() {
        return transactionCategoryRepository.queryAll();
    }

    @GetMapping("/total")
    public Mono<TransactionTypeTotal> getTransactionTotal(
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            @RequestParam(name = "transactionTypeId") int transactionTypeId,
            @RequestParam(name = "budgetIds") Set<Long> budgetIds) {

        return this.transactionRepository.getBudgetSummaries(month, year, budgetIds, transactionTypeId)
                .map(sum -> new TransactionTypeTotal(sum.getTransactionType(), sum.getActual()))
                .switchIfEmpty(
                        this.transactionTypeRepository.findById(transactionTypeId)
                                .map(tt -> new TransactionTypeTotal(tt.getTransactionTypeName(), BigDecimal.ZERO))
                                .switchIfEmpty(Mono.empty().ofType(TransactionTypeTotal.class)));
    }
}
