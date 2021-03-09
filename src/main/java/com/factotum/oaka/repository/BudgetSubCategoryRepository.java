package com.factotum.oaka.repository;

import com.factotum.oaka.model.BudgetSubCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetSubCategoryRepository extends ReactiveMongoRepository<BudgetSubCategory, Long> {
}
