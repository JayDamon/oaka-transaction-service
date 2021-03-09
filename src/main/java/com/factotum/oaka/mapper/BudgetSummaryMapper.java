package com.factotum.oaka.mapper;

import com.factotum.oaka.dto.TransactionBudgetSummary;
import io.r2dbc.spi.Row;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.function.BiFunction;

@Slf4j
public class BudgetSummaryMapper implements BiFunction<Row, Object, TransactionBudgetSummary> {
    @Override
    public TransactionBudgetSummary apply(Row row, Object o) {

        return new TransactionBudgetSummary(
                row.get("transaction_type", String.class),
                row.get("month", Integer.class),
                row.get("year", Integer.class),
                row.get("sum", BigDecimal.class)
        );

    }
}
