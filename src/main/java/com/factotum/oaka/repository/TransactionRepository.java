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

    Mono<Transaction> findByIdAndTenantId(long id, String tenantId);

    @Query("SELECT * FROM transaction t " +
            "LEFT JOIN transaction_category tc ON tc.transaction_category_id = t.transaction_category_id " +
            "LEFT JOIN transaction_sub_category bsg ON bsg.transaction_sub_category_id = tc.transaction_sub_category_id " +
            "WHERE t.tenant_id = :tenantId " +
            "ORDER BY transaction_date DESC")
    Flux<TransactionDto> findAllByOrderByDateDesc(@Param("tenantId") String tenantId);

    @Query("SELECT month(t.transaction_date) as month, year(t.transaction_date) as year, ABS(SUM(t.amount)) as sum " +
            "FROM transaction t " +
            "WHERE month(t.transaction_date) = :month " +
            "  AND year(transaction_date) = :year " +
            "  AND t.budget_id in (:budgetIds) " +
            "  AND t.amount >= 0 " +
            "  AND t.tenant_id = :tenantId " +
            "GROUP BY month(t.transaction_date), year(t.transaction_date) " +
            "ORDER BY month(t.transaction_date), year(t.transaction_date) DESC;")
    Mono<TransactionBudgetSummary> getIncomeTransactionSummary(int month, int year, Set<Long> budgetIds, String tenantId);

    @Query("SELECT month(t.transaction_date) as month, year(t.transaction_date) as year, ABS(SUM(t.amount)) as sum " +
            "FROM transaction t " +
            "WHERE month(t.transaction_date) = :month " +
            "  AND year(transaction_date) = :year " +
            "  AND t.budget_id in (:budgetIds) " +
            "  AND t.amount < 0 " +
            "  AND t.tenant_id = :tenantId " +
            "GROUP BY month(t.transaction_date), year(t.transaction_date) " +
            "ORDER BY month(t.transaction_date), year(t.transaction_date) DESC;")
    Mono<TransactionBudgetSummary> getExpenseTransactionSummary(int month, int year, Set<Long> budgetIds, String tenantId);

}
