package com.protean.moneymaker.oaka.service;

import com.protean.moneymaker.rin.dto.TransactionDto;
import com.protean.moneymaker.rin.model.Transaction;

import java.util.List;
import java.util.Set;

public interface TransactionRetrievalService {

    List<Transaction> getAllTransactions();

    Set<TransactionDto> getAllTransactionDtos();



}
