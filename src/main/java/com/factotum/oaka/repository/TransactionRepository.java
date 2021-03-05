package com.factotum.oaka.repository;

import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.model.Transaction;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, Long>
//        , BudgetSummaryRepository
{

    Flux<Transaction> findAllByOrderByDateDesc();

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
    Mono<TransactionBudgetSummary> getBudgetSummaries(
            int year, int month, Set<Long> budgetIds, int tTypeId);

}
