package com.factotum.transactionservice.converter;

import com.factotum.transactionservice.message.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@Slf4j
@WritingConverter
public class LocationJsonWriter implements Converter<Location, Json> {

    private final ObjectMapper objectMapper;

    public LocationJsonWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Json convert(Location source) {

        try {
            return Json.of(this.objectMapper.writeValueAsString(source));
        } catch (JsonProcessingException e) {
            log.error("Error converting Location to json: {}", source, e);
        }
        return Json.of("");
    }
}
