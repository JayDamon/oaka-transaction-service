package com.factotum.oaka.repository;

import com.factotum.oaka.model.RecurringTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
}
