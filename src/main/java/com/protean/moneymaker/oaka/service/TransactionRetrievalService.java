package com.protean.moneymaker.oaka.service;

import com.protean.moneymaker.rin.model.Transaction;

import java.util.List;

public interface TransactionRetrievalService {

    public List<Transaction> getAllTransactions();

}
