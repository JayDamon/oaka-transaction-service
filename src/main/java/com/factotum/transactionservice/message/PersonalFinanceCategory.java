package com.factotum.transactionservice.message;

import com.factotum.transactionservice.model.ConfidenceLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalFinanceCategory {

    @JsonProperty("primary")
    private String primary;

    @JsonProperty("detailed")
    private String detailed;

    @JsonProperty("confidence_level")
    private ConfidenceLevel confidenceLevel;

}
