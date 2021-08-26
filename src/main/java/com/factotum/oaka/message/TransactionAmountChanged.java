package com.factotum.oaka.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

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
    private Long accountId;

}
