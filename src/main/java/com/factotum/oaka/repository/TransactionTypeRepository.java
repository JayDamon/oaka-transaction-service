package com.factotum.oaka.repository;

import com.factotum.oaka.model.TransactionType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends ReactiveMongoRepository<TransactionType, Integer> {
}
