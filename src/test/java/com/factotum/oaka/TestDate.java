package com.factotum.oaka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TestDate {

    @Test
    void test() throws JsonProcessingException {
        String json = "{\"date\":\"2018-12-31T00:00:00\"}";

//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

//        LocalDateTimeSerializer serializer = new LocalDateTimeSerializer(formatter);
//
//        JavaTimeModule module = new JavaTimeModule();
//        module.addSerializer(serializer);

        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(module);
        mapper.findAndRegisterModules();

        Something something = mapper.readValue(json, Something.class);

        System.out.println(something.toString());
    }

    @ToString
    @NoArgsConstructor
    @Data
    public static class Something {

        //        @JsonProperty("date")
        private LocalDateTime date;

    }

}
