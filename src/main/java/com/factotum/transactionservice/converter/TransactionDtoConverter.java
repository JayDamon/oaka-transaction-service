package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.dto.BudgetDto;
import com.factotum.transactionservice.dto.BudgetSubCategoryDto;
import com.factotum.transactionservice.dto.ShortAccountDto;
import com.factotum.transactionservice.dto.TransactionCategoryDto;
import com.factotum.transactionservice.dto.TransactionDto;
import io.r2dbc.spi.Row;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@ReadingConverter
public class TransactionDtoConverter implements Converter<Row, TransactionDto> {
    @Override
    public TransactionDto convert(Row source) {

        ShortAccountDto account = new ShortAccountDto();
        account.setId(source.get("account_id", UUID.class));

        BudgetDto budget = null;
        UUID budgetId = source.get("budget_id", UUID.class);
        if (budgetId != null) {
            budget = new BudgetDto();
            budget.setId(budgetId);
        }

        BudgetSubCategoryDto budgetSubCategory = new BudgetSubCategoryDto(
                source.get("transaction_sub_category_id", UUID.class),
                source.get("sub_category_name", String.class)
        );

        TransactionCategoryDto transactionCategory = new TransactionCategoryDto(
                source.get("transaction_category_id", UUID.class),
                source.get("category_name", String.class),
                budgetSubCategory
        );

        return new TransactionDto(
                source.get("transaction_id", UUID.class),
                source.get("amount", BigDecimal.class),
                source.get("description", String.class),
                source.get("transaction_date", LocalDate.class),
                account,
                budget,
                transactionCategory
        );
    }
}
