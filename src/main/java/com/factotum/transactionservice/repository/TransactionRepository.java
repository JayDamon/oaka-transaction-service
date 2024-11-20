package com.factotum.transactionservice.repository;

import com.factotum.transactionservice.dto.TransactionBudgetSummary;
import com.factotum.transactionservice.dto.TransactionDto;
import com.factotum.transactionservice.model.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, UUID> {

    Mono<Transaction> findByIdAndTenantId(UUID id, String tenantId);
    Mono<Transaction> findByPlaidTransactionIdAndTenantId(String plaidTransactionId, String tenantId);
    Mono<Void> deleteByPlaidTransactionIdAndTenantId(String plaidTransactionId, String tenantId);

    @Query("SELECT * FROM transaction t " +
            "WHERE t.tenant_id = :tenantId " +
            "ORDER BY transaction_date DESC")
    Flux<TransactionDto> findAllByOrderByDateDesc(@Param("tenantId") String tenantId);

    @Query("SELECT DATE_PART('month', t.transaction_date) as month, DATE_PART('year', t.transaction_date) as year, ABS(SUM(t.amount)) as sum " +
            "FROM transaction t " +
            "WHERE DATE_PART('month', t.transaction_date) = :month " +
            "  AND DATE_PART('year', t.transaction_date) = :year " +
            "  AND t.budget_id in (:budgetIds) " +
            "  AND t.amount >= 0 " +
            "  AND t.tenant_id = :tenantId " +
            "GROUP BY DATE_PART('month', t.transaction_date), DATE_PART('year', t.transaction_date) " +
            "ORDER BY DATE_PART('month', t.transaction_date), DATE_PART('year', t.transaction_date) DESC;")
    Mono<TransactionBudgetSummary> getIncomeTransactionSummary(int month, int year, Set<UUID> budgetIds, String tenantId);

    @Query("""
            SELECT DATE_PART('month', t.transaction_date) as month, DATE_PART('year', t.transaction_date) as year, ABS(SUM(t.amount)) as sum 
            FROM transaction t 
            WHERE DATE_PART('month', t.transaction_date) = :month 
              AND DATE_PART('year', t.transaction_date) = :year 
              AND t.budget_id in (:budgetIds) 
              AND t.amount < 0 
              AND t.tenant_id = :tenantId 
            GROUP BY DATE_PART('month', t.transaction_date), DATE_PART('year', t.transaction_date) 
            ORDER BY DATE_PART('month', t.transaction_date), DATE_PART('year', t.transaction_date) DESC
            """)
    Mono<TransactionBudgetSummary> getExpenseTransactionSummary(int month, int year, Set<UUID> budgetIds, String tenantId);

}
