package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.message.TransactionCounterparty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.List;

@Slf4j
@WritingConverter
public class TransactionCounterPartyListJsonWriter implements Converter<List<TransactionCounterparty>, Json> {

    private final ObjectMapper objectMapper;

    public TransactionCounterPartyListJsonWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Json convert(List<TransactionCounterparty> source) {

        try {
            return Json.of(this.objectMapper.writeValueAsString(source));
        } catch (JsonProcessingException e) {
            log.error("Error converting transaction counterparty list to json: {}", source, e);
        }

        return Json.of("");
    }
}
