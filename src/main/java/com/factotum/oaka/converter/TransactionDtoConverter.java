package com.factotum.oaka.converter;

import com.factotum.oaka.dto.BudgetDto;
import com.factotum.oaka.dto.BudgetSubCategoryDto;
import com.factotum.oaka.dto.ShortAccountDto;
import com.factotum.oaka.dto.TransactionCategoryDto;
import com.factotum.oaka.dto.TransactionDto;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ReadingConverter
public class TransactionDtoConverter implements Converter<Row, TransactionDto> {
    @Override
    public TransactionDto convert(Row source) {
        ShortAccountDto account = new ShortAccountDto();
        account.setId(source.get("account_id", Long.class));

        BudgetDto budget = new BudgetDto();
        budget.setId(source.get("budget_id", Long.class));

        BudgetSubCategoryDto budgetSubCategory = new BudgetSubCategoryDto(
                source.get("budget_sub_category_id", Long.class),
                source.get("sub_category_name", String.class)
        );

        TransactionCategoryDto transactionCategory = new TransactionCategoryDto(
                source.get("transaction_category_id", Long.class),
                source.get("category_name", String.class),
                budgetSubCategory
        );

        return new TransactionDto(
                source.get("transaction_id", Long.class),
                source.get("amount", BigDecimal.class),
                source.get("description", String.class),
                source.get("transaction_date", LocalDateTime.class),
                account,
                budget,
                transactionCategory
        );
    }
}
