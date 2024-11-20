package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.message.TransactionCounterparty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ReadingConverter
public class TransactionCounterPartyListConverter implements Converter<Json, List<TransactionCounterparty>> {

   private final ObjectMapper objectMapper;

    public TransactionCounterPartyListConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<TransactionCounterparty> convert(Json source) {

        try {
            return this.objectMapper.readValue(source.asString(), new TypeReference<>() {});
        } catch (IOException e) {
            log.error("Error converting json to Transaction Counter Party List: {}", source, e);
        }

        return new ArrayList<>();
    }
}
