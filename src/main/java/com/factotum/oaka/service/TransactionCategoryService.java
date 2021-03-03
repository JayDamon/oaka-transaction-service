package com.factotum.oaka.service;

import com.factotum.oaka.model.TransactionCategory;
import reactor.core.publisher.Flux;

public interface TransactionCategoryService {

    Flux<TransactionCategory> findAllTransactionCategories();

}
