package com.factotum.oaka.repository;

import com.factotum.oaka.model.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long>, BudgetSummaryRepository {

    Flux<Transaction> findAllByOrderByDateDesc();

//    @Query(value = "SELECT " +
//            "new com.factotum.oaka.dto.TransactionBudgetSummary(" +
//            "tt.transactionTypeName, " +
//            "month(t.date), year(t.date), " +
//            "ABS(SUM(t.amount))) " +
//            "FROM Transaction As t " +
//            "INNER JOIN t.transactionType as tt " +
//            "WHERE month(t.date) = :month " +
//            "   AND year(t.date) = :year " +
//            "   AND t.budgetId in :budgetIds " +
//            "   AND tt.id = :tTypeId " +
//            "GROUP BY month(t.date), year(t.date),  tt.transactionTypeName " +
//            "ORDER BY year(t.date), month(t.date), tt.transactionTypeName DESC")
//    @Query("SELECT * FROM transaction")
//    Mono<TransactionBudgetSummary> getBudgetSummaries(
//            int year, int month, Set<Long> budgetIds, int tTypeId);

}
