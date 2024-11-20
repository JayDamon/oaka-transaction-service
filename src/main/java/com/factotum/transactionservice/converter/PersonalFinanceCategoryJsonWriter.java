package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.message.PersonalFinanceCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.data.convert.WritingConverter;

@Slf4j
@WritingConverter
public class PersonalFinanceCategoryJsonWriter implements Converter<PersonalFinanceCategory, Json> {

    private final ObjectMapper mapper;

    public PersonalFinanceCategoryJsonWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Json convert(PersonalFinanceCategory source) {

        try {
            return Json.of(this.mapper.writeValueAsString(source));
        } catch (JsonProcessingException e) {
            log.error("Error converting personal finance category to json: {}", source, e);
        }

        return Json.of("");
    }
}
