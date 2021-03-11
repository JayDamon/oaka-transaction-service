package com.factotum.oaka.repository;

import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.model.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {

    @Query("SELECT * FROM transaction t " +
            "INNER JOIN transaction_category tc ON tc.transaction_category_id = t.transaction_category_id " +
            "INNER JOIN budget_sub_category bsg ON bsg.budget_sub_category_id = tc.budget_sub_category_id " +
            "ORDER BY transaction_date DESC")
    Flux<TransactionDto> findAllByOrderByDateDesc();

//    Flux<Transaction> findAllByOrderByDateDesc();

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

    @Query("SELECT tt.transaction_type, month(t.transaction_date) as month, year(t.transaction_date) as year, ABS(SUM(t.amount)) as sum " +
            "FROM transaction t " +
            "INNER JOIN transaction_type tt on tt.transaction_type_id = t.transaction_type_id " +
            "WHERE month(t.transaction_date) = :month " +
            "  AND year(transaction_date) = :year " +
            "  AND t.budget_id in (:budgetIds) " +
            "  AND tt.transaction_type_id = :typeId " +
            "GROUP BY month(t.transaction_date), year(t.transaction_date), tt.transaction_type " +
            "ORDER BY month(t.transaction_date), year(t.transaction_date), tt.transaction_type DESC;")
    Mono<TransactionBudgetSummary> getBudgetSummaries(int month, int year, Set<Long> budgetIds, int typeId);

}
