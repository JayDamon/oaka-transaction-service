package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.dto.BudgetDto;
import com.factotum.transactionservice.dto.ShortAccountDto;
import com.factotum.transactionservice.dto.TransactionDto;
import com.factotum.transactionservice.message.PersonalFinanceCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private ObjectMapper objectMapper;

    public TransactionDtoConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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

        PersonalFinanceCategory personalFinanceCategory = null;
        try {
            if (source.get("personal_finance_category") != null) {
                personalFinanceCategory = this.objectMapper.readValue(source.get("personal_finance_category", String.class), new TypeReference<>() {});
            }
        } catch (JsonProcessingException e) {
            log.error("Failed to parse personal_finance_category {}", source.get("personal_finance_category"), e);
        }

        return new TransactionDto(
                source.get("transaction_id", UUID.class),
                source.get("amount", BigDecimal.class),
                source.get("description", String.class),
                source.get("transaction_date", LocalDate.class),
                account,
                budget,
                personalFinanceCategory
        );
    }
}
