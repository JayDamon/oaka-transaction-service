package com.protean.moneymaker.oaka.service;

import com.protean.moneymaker.oaka.dto.TransactionDto;
import com.protean.moneymaker.oaka.model.Transaction;

import java.util.List;
import java.util.Set;

public interface TransactionRetrievalService {

    List<Transaction> getAllTransactions();

    Set<TransactionDto> getAllTransactionDtos();

}
