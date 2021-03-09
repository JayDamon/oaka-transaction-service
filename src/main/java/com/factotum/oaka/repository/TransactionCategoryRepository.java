package com.factotum.oaka.repository;

import com.factotum.oaka.model.TransactionCategory;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCategoryRepository extends ReactiveMongoRepository<TransactionCategory, Long> {
}
