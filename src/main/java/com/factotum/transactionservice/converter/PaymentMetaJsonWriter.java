package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.message.Location;
import com.factotum.transactionservice.message.PaymentMeta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@Slf4j
@WritingConverter
public class PaymentMetaJsonWriter implements Converter<PaymentMeta, Json> {

    private final ObjectMapper objectMapper;

    public PaymentMetaJsonWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Json convert(PaymentMeta source) {

        try {
            return Json.of(this.objectMapper.writeValueAsString(source));
        } catch (JsonProcessingException e) {
            log.error("Error converting PaymentMeta to json: {}", source, e);
        }
        return Json.of("");
    }
}
