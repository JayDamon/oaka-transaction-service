package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.message.PersonalFinanceCategory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;

@Slf4j
@ReadingConverter
public class PersonalFinanceCategoryConverter implements Converter<Json, PersonalFinanceCategory> {

    private final ObjectMapper objectMapper;

    public PersonalFinanceCategoryConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public PersonalFinanceCategory convert(Json source) {

        try {
            return objectMapper.readValue(source.asString(), new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("Error converting personal finance category to personal finance category: {}", source, e);
        }
        return null;
    }
}
