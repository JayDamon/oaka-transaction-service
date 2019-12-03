package com.protean.moneymaker.oaka.controller;

import com.protean.moneymaker.oaka.service.TransactionRetrievalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    private TransactionRetrievalService transactionRetrievalService;

    public TransactionController(TransactionRetrievalService transactionRetrievalService) {
        this.transactionRetrievalService = transactionRetrievalService;
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAllTransactions() {
        // TODO probably want to paginate this
        return ok(transactionRetrievalService.getAllTransactionDtos());

    }

}
