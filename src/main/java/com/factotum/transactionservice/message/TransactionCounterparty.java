package com.factotum.transactionservice.message;

import com.factotum.transactionservice.enumeration.CounterpartyType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionCounterparty {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private CounterpartyType type;

    @JsonProperty("website")
    private String website;

    @JsonProperty("logo_url")
    private String logoUrl;

}
