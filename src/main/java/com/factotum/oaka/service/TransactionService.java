package com.factotum.oaka.service;

import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.model.Transaction;
import reactor.core.publisher.Flux;

public interface TransactionService {

    Flux<Transaction> getAllTransactions();

    Flux<TransactionDto> getAllTransactionDtos();

}
