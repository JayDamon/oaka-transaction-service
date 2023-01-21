package com.factotum.transactionservice.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionAmountChanged implements Serializable {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("accountId")
    private UUID accountId;

}
