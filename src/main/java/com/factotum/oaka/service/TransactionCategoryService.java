package com.factotum.oaka.service;

import com.factotum.oaka.model.TransactionCategory;

import java.util.List;

public interface TransactionCategoryService {

    List<TransactionCategory> findAllTransactionCategories();

}
