package com.factotum.oaka.controller;

import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.dto.TransactionTypeTotal;
import com.factotum.oaka.model.TransactionCategory;
import com.factotum.oaka.model.TransactionType;
import com.factotum.oaka.repository.TransactionRepository;
import com.factotum.oaka.repository.TransactionTypeRepository;
import com.factotum.oaka.service.TransactionCategoryService;
import com.factotum.oaka.service.TransactionService;
import com.factotum.oaka.util.TransactionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/transactions")
@Validated
public class TransactionController {

    private final TransactionCategoryService transactionCategoryService;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionController(
            TransactionCategoryService transactionCategoryService,
            TransactionService transactionService,
            TransactionRepository transactionRepository,
            TransactionTypeRepository transactionTypeRepository) {
        this.transactionCategoryService = transactionCategoryService;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTransactions() {
        // TODO probably want to paginate this
        return ok(transactionService.getAllTransactionDtos());

    }

    @GetMapping("/categories")
    public ResponseEntity<?> getTransactionCategories() {
        List<TransactionCategory> categories = transactionCategoryService.findAllTransactionCategories();

        return ok(TransactionUtil.mapEntityListToDtoCollection(categories));
    }

    @PostMapping("")
    public ResponseEntity<?> saveNewTransactions(
            @RequestBody @Valid Set<TransactionDto> transactions) {

        return ok(TransactionUtil.mapEntityCollectionToDtos(this.transactionService.saveAllTransactions(transactions)));
    }

    @GetMapping("/total")
    public ResponseEntity<TransactionTypeTotal> getTransactionTotal(
            @RequestParam(name = "year") int year,
            @RequestParam(name = "month") int month,
            @RequestParam(name = "transactionTypeId") int transactionTypeId,
            @RequestParam(name = "budgetIds") Set<Long> budgetIds) {

        Optional<TransactionBudgetSummary> summary = this.transactionRepository.getBudgetSummaries(year, month, budgetIds, transactionTypeId);

        if (summary.isEmpty()) {
            Optional<TransactionType> transactionType = this.transactionTypeRepository.findById(transactionTypeId);
            if (transactionType.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ok(new TransactionTypeTotal(transactionType.get().getTransactionTypeName(), BigDecimal.ZERO));
        }
        return ok(new TransactionTypeTotal(summary.get().getTransactionType(), summary.get().getActual()));
    }
}
