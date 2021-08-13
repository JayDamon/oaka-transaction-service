package com.factotum.oaka.service;

import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.model.Transaction;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Flux<TransactionDto> getAllTransactionDtos(Jwt jwt);

    Mono<Transaction> updateTransaction(Jwt jwt, TransactionDto transaction);
}
