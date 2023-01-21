package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.dto.BudgetSubCategoryDto;
import com.factotum.transactionservice.dto.TransactionCategoryDto;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.UUID;

@ReadingConverter
public class TransactionCategoryConverter implements Converter<Row, TransactionCategoryDto> {
    @Override
    public TransactionCategoryDto convert(Row source) {

        BudgetSubCategoryDto budgetSubCategory = new BudgetSubCategoryDto(
                source.get("transaction_sub_category_id", UUID.class),
                source.get("sub_category_name", String.class)
        );

        return new TransactionCategoryDto(
                source.get("transaction_category_id", UUID.class),
                source.get("category_name", String.class),
                budgetSubCategory
        );
    }
}
