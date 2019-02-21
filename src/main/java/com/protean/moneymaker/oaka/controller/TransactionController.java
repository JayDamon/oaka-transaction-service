package com.protean.moneymaker.oaka.controller;

import com.protean.moneymaker.oaka.service.TransactionRetrievalService;
import com.protean.security.auron.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/transactions")
public class TransactionController {

    private TransactionRetrievalService transactionRetrievalService;

    public TransactionController(TransactionRetrievalService transactionRetrievalService) {
        this.transactionRetrievalService = transactionRetrievalService;
    }

    @GetMapping(value = "/transaction")
    public ResponseEntity<?> getAllTransactions() {
        // TODO need to create a transaction dto
        return new ResponseEntity<>(new StandardResponse<>(HttpStatus.OK, transactionRetrievalService.getAllTransactions()), HttpStatus.OK);

    }
}
