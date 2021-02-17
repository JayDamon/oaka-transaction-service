package com.protean.moneymaker.oaka.repository;

import com.protean.moneymaker.oaka.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Set<Transaction> findAllByOrderByDateDesc();

    Set<Transaction> findAllByDateAfterAndDateBefore(ZonedDateTime startDate, ZonedDateTime after);

//    @Query(value = "SELECT " +
//            "new com.protean.moneymaker.rin.dto.TransactionBudgetSummary(" +
//                "tt.transactionTypeName, bct.name, " +
//                "month(t.date), year(t.date), " +
//                "SUM(b.amount * f.monthFactor), ABS(SUM(t.amount)), " +
//            "CASE " +
//            "   WHEN tt.transactionTypeName = 'Income' AND " +
//            "       SUM(b.amount * f.monthFactor) > SUM(t.amount) THEN false " +
//            "   WHEN tt.transactionTypeName = 'Expense' AND " +
//            "       SUM(b.amount * f.monthFactor) < (ABS(SUM(t.amount))) THEN false " +
//            "   ELSE true END) " +
//            "FROM Transaction As t " +
//            "INNER JOIN t.budget as b " +
//            "INNER JOIN b.frequencyType f " +
//            "INNER JOIN b.budgetCategory bc " +
//            "INNER JOIN bc.type as bct " +
//            "INNER JOIN t.transactionType as tt " +
//            "WHERE month(t.date) = :month AND year(t.date) = :year " +
//            "   AND bct.id = :typeId AND tt.id = :tTypeId " +
//            "GROUP BY month(t.date), year(t.date),  bct.name, tt.transactionTypeName " +
//            "ORDER BY year(t.date), month(t.date), tt.transactionTypeName DESC, bct.name")
//    Optional<TransactionBudgetSummary> getBudgetSummaries(
//            @Param("year") int year,
//            @Param("month") int month,
//            @Param("typeId") int budgetCategoryTypeId,
//            @Param("tTypeId") int transactionTypeId);

}
