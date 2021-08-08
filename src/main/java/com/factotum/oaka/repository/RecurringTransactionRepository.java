package com.factotum.oaka.repository;

import com.factotum.oaka.model.RecurringTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringTransactionRepository extends org.springframework.data.repository.Repository<RecurringTransaction, Long> {
}
