package com.factotum.oaka.repository;

import com.factotum.oaka.dto.TransactionBudgetSummary;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Set;

@Repository
public class BudgetSummaryRepositoryImpl implements BudgetSummaryRepository {

//    private final DatabaseClient databaseClient;
//
//    public BudgetSummaryRepositoryImpl(DatabaseClient databaseClient) {
//        this.databaseClient = databaseClient;
//    }
//
@Override
public Mono<TransactionBudgetSummary> getBudgetSummaries(int month, int year, Set<Long> budgetIds, int tTypeId) {
//
//        String sql = "SELECT tt.transaction_type, month(t.transaction_date) as month, year(t.transaction_date) as year, ABS(SUM(t.amount)) as sum " +
//                "FROM transaction t " +
//                "INNER JOIN transaction_type tt on tt.transaction_type_id = t.transaction_type_id " +
//                "WHERE month(t.transaction_date) = :month " +
//                "  AND year(transaction_date) = :year " +
//                "  AND t.budget_id in (:budgetIds) " +
//                "  AND tt.transaction_type_id = :typeId " +
//                "GROUP BY month(t.transaction_date), year(t.transaction_date), tt.transaction_type " +
//                "ORDER BY month(t.transaction_date), year(t.transaction_date), tt.transaction_type DESC;";
//
//        return this.databaseClient
//                .sql(sql)
//                .bind("month", month)
//                .bind("year", year)
//                .bind("budgetIds", budgetIds)
//                .bind("typeId", tTypeId)
//                .map(new BudgetSummaryMapper()::apply).one();
    return null;
}

}
