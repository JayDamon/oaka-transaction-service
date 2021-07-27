package com.factotum.oaka.service;

import com.factotum.oaka.model.TransactionCategory;
import com.factotum.oaka.repository.TransactionCategoryRepository;
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
