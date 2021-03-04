package com.factotum.oaka.repository;

import com.factotum.oaka.dto.TransactionBudgetSummary;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface BudgetSummaryRepository {

    Mono<TransactionBudgetSummary> getBudgetSummaries(int month, int year, Set<Long> budgetIds, int tTypeId);

}
