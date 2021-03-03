package com.factotum.oaka.repository;

import com.factotum.oaka.model.TransactionType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends ReactiveCrudRepository<TransactionType, Integer> {
}
