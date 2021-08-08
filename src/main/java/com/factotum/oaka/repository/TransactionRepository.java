package com.factotum.oaka.repository;

import com.factotum.oaka.dto.TransactionBudgetSummary;
import com.factotum.oaka.dto.TransactionDto;
import com.factotum.oaka.model.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {

    @Query("SELECT * FROM transaction t " +
            "LEFT JOIN transaction_category tc ON tc.transaction_category_id = t.transaction_category_id " +
            "LEFT JOIN transaction_sub_category bsg ON bsg.transaction_sub_category_id = tc.transaction_sub_category_id " +
            "WHERE t.tenant_id = :tenantId " +
            "ORDER BY transaction_date DESC")
    Flux<TransactionDto> findAllByOrderByDateDesc(@Param("tenantId") String tenantId);

    @Query("SELECT tt.transaction_type, month(t.transaction_date) as month, year(t.transaction_date) as year, ABS(SUM(t.amount)) as sum " +
            "FROM transaction t " +
            "INNER JOIN transaction_type tt on tt.transaction_type_id = t.transaction_type_id " +
            "WHERE month(t.transaction_date) = :month " +
            "  AND year(transaction_date) = :year " +
            "  AND t.budget_id in (:budgetIds) " +
            "  AND tt.transaction_type_id = :typeId " +
            "  AND t.tenant_id = :tenantId " +
            "GROUP BY month(t.transaction_date), year(t.transaction_date), tt.transaction_type " +
            "ORDER BY month(t.transaction_date), year(t.transaction_date), tt.transaction_type DESC;")
    Mono<TransactionBudgetSummary> getBudgetSummaries(int month, int year, Set<Long> budgetIds, int typeId, String tenantId);

}
