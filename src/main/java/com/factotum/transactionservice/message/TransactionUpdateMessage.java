package com.factotum.transactionservice.message;

import com.factotum.transactionservice.model.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionUpdateMessage {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("accountId")
    private UUID accountId;

    @JsonProperty("transactions")
    private List<TransactionMessage> transactions;

}
