package com.factotum.oaka.repository;

import com.factotum.oaka.model.BudgetSubCategory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetSubCategoryRepository extends ReactiveCrudRepository<BudgetSubCategory, Long> {
}
