package com.factotum.oaka.repository;

import com.factotum.oaka.model.TransactionSubCategory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetSubCategoryRepository extends ReactiveCrudRepository<TransactionSubCategory, Long> {
}
