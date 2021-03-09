package com.factotum.oaka.controller;

import com.factotum.oaka.dto.TransactionCategoryDto;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.dto.TransactionTypeTotal;
import com.factotum.oaka.repository.TransactionRepository;
import com.factotum.oaka.repository.TransactionTypeRepository;
import com.factotum.oaka.service.TransactionCategoryService;
import com.factotum.oaka.service.TransactionService;
import com.factotum.oaka.util.TransactionUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/v1/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionCategoryService transactionCategoryService;
    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionController(
            TransactionService transactionService,
            TransactionCategoryService transactionCategoryService,
            TransactionRepository transactionRepository,
            TransactionTypeRepository transactionTypeRepository) {
        this.transactionService = transactionService;
        this.transactionCategoryService = transactionCategoryService;
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @GetMapping("")
    public Flux<TransactionDto> getAllTransactions() {
        // TODO probably want to paginate this
//        return transactionRepository.findAll();
        return transactionService.getAllTransactionDtos();

    }

    @GetMapping("/categories")
    public Flux<TransactionCategoryDto> getTransactionCategories() {
        return transactionCategoryService.findAllTransactionCategories()
                .map(TransactionUtil::mapCategoryEntityToDto);
    }

    // TODO: need to figure out if this is actually necessary
//    @PostMapping("")
//    public Flux<TransactionDto> saveNewTransactions(
//            @RequestBody @Valid Set<TransactionDto> transactions) {
//
//        return this.transactionService.saveAllTransactions(transactions)
//                .flatMap(t -> TransactionUtil.mapEntityToDto(t))
//                .then(t ->
//                    this.budgetSubCategoryRepository.findById(t.get)
//                );
//    }

    @GetMapping("/total")
    public Mono<TransactionTypeTotal> getTransactionTotal(
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            @RequestParam(name = "transactionTypeId") int transactionTypeId,
            @RequestParam(name = "budgetIds") Set<Long> budgetIds) {

        return this.transactionRepository.getBudgetSummaries(year, month, budgetIds, transactionTypeId)
                .map(sum -> new TransactionTypeTotal(sum.getTransactionType(), sum.getActual()))
                .switchIfEmpty(
                        this.transactionTypeRepository.findById(transactionTypeId)
                                .map(tt -> new TransactionTypeTotal(tt.getTransactionTypeName(), BigDecimal.ZERO))
                                .switchIfEmpty(Mono.empty().ofType(TransactionTypeTotal.class)));
    }
}
