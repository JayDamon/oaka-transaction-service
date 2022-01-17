package com.factotum.transactionservice.service;

import com.factotum.transactionservice.dto.TransactionDto;
import com.factotum.transactionservice.model.Transaction;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Flux<TransactionDto> getAllTransactionDtos(Jwt jwt);

    Mono<Transaction> updateTransaction(Jwt jwt, TransactionDto transaction);
}
