package com.protean.moneymaker.oaka.controller;

import com.protean.moneymaker.oaka.service.TransactionRetrievalService;
import com.protean.moneymaker.rin.model.TransactionCategory;
import com.protean.moneymaker.rin.service.TransactionCategoryService;
import com.protean.moneymaker.rin.util.TransactionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    private TransactionRetrievalService transactionRetrievalService;
    private TransactionCategoryService transactionCategoryService;

    public TransactionController(TransactionRetrievalService transactionRetrievalService, TransactionCategoryService transactionCategoryService) {
        this.transactionRetrievalService = transactionRetrievalService;
        this.transactionCategoryService = transactionCategoryService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTransactions() {
        // TODO probably want to paginate this
        return ok(transactionRetrievalService.getAllTransactionDtos());

    }

    @GetMapping("/categories")
    public ResponseEntity<?> getTransactionCategories() {
        List<TransactionCategory> categories = transactionCategoryService.findAllTransactionCategories();

        return ok(TransactionUtil.mapEntityListToDtoCollection(categories));
    }

}
