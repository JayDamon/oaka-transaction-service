package com.factotum.transactionservice.service;

import com.factotum.transactionservice.model.TransactionCategory;
import com.factotum.transactionservice.repository.TransactionCategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class TransactionCategoryServiceImpl implements TransactionCategoryService {

    private final TransactionCategoryRepository transactionCategoryRepository;

    public TransactionCategoryServiceImpl(TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    @Override
    public Flux<TransactionCategory> findAllTransactionCategories() {
        return transactionCategoryRepository.findAll();
    }
}
