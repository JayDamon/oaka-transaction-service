package com.factotum.oaka.repository;

import com.factotum.oaka.model.RecurringTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringTransactionRepository extends ReactiveMongoRepository<RecurringTransaction, Long> {
}
