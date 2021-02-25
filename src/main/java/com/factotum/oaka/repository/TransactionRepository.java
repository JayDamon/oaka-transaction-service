package com.factotum.oaka.repository;

import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Set<Transaction> findAllByOrderByDateDesc();

    @Query(value = "SELECT " +
            "new com.factotum.oaka.dto.TransactionBudgetSummary(" +
            "tt.transactionTypeName, " +
            "month(t.date), year(t.date), " +
            "ABS(SUM(t.amount))) " +
            "FROM Transaction As t " +
            "INNER JOIN t.transactionType as tt " +
            "WHERE month(t.date) = :month " +
            "   AND year(t.date) = :year " +
            "   AND t.budgetId in :budgetIds " +
            "   AND tt.id = :tTypeId " +
            "GROUP BY month(t.date), year(t.date),  tt.transactionTypeName " +
            "ORDER BY year(t.date), month(t.date), tt.transactionTypeName DESC")
    Optional<TransactionBudgetSummary> getBudgetSummaries(
            int year, int month, Set<Long> budgetIds, int tTypeId);

}
