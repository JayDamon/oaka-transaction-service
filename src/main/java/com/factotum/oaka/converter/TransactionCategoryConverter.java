package com.factotum.oaka.converter;

import com.factotum.oaka.dto.BudgetSubCategoryDto;
import com.factotum.oaka.dto.TransactionCategoryDto;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class TransactionCategoryConverter implements Converter<Row, TransactionCategoryDto> {
    @Override
    public TransactionCategoryDto convert(Row source) {

        BudgetSubCategoryDto budgetSubCategory = new BudgetSubCategoryDto(
                source.get("budget_sub_category_id", Long.class),
                source.get("sub_category_name", String.class)
        );

        return new TransactionCategoryDto(
                source.get("transaction_category_id", Long.class),
                source.get("category_name", String.class),
                budgetSubCategory
        );
    }
}
