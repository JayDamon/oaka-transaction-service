package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.dto.TransactionBudgetSummary;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;

@ReadingConverter
public class BudgetSummaryConverter implements Converter<Row, TransactionBudgetSummary> {
    @Override
    public TransactionBudgetSummary convert(Row source) {
        return new TransactionBudgetSummary(
                source.get("month", Integer.class),
                source.get("year", Integer.class),
                source.get("sum", BigDecimal.class)
        );
    }
}
