package com.protean.moneymaker.oaka.service;

import com.protean.moneymaker.oaka.model.TransactionCategory;

import java.util.List;

public interface TransactionCategoryService {

    List<TransactionCategory> findAllTransactionCategories();

}
