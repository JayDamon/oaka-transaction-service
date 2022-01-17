package com.factotum.transactionservice.service;

import com.factotum.transactionservice.model.TransactionCategory;
import reactor.core.publisher.Flux;

public interface TransactionCategoryService {

    Flux<TransactionCategory> findAllTransactionCategories();

}
