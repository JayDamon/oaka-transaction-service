package com.protean.moneymaker.oaka.controller;

import com.protean.moneymaker.oaka.dto.TransactionDto;
import com.protean.moneymaker.oaka.model.TransactionCategory;
import com.protean.moneymaker.oaka.service.TransactionCategoryService;
import com.protean.moneymaker.oaka.service.TransactionService;
import com.protean.moneymaker.oaka.util.TransactionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/transactions")
@Validated
public class TransactionController {

    private final TransactionCategoryService transactionCategoryService;
    private final TransactionService transactionService;

    public TransactionController(
            TransactionCategoryService transactionCategoryService,
            TransactionService transactionService) {
        this.transactionCategoryService = transactionCategoryService;
        this.transactionService = transactionService;
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

}
