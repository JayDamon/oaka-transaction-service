package com.factotum.oaka.service;

import com.factotum.oaka.model.TransactionCategory;
import com.factotum.oaka.repository.TransactionCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionCategoryServiceImpl implements TransactionCategoryService {

    private static final Logger log = LoggerFactory.getLogger(TransactionCategoryServiceImpl.class);

    private final TransactionCategoryRepository transactionCategoryRepository;

    public TransactionCategoryServiceImpl(TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
    }

    @Override
    public List<TransactionCategory> findAllTransactionCategories() {

        return transactionCategoryRepository.findAll();
    }
}
