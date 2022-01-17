package com.factotum.transactionservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionBudgetSummary {

    @JsonProperty("category")
    private String category;

    @JsonProperty("month")
    private Integer month;

    @JsonProperty("monthText")
    private String monthText;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("planned")
    private BigDecimal planned;

    @JsonProperty("actual")
    private BigDecimal actual;

    @JsonProperty("expected")
    private boolean expected;

    public TransactionBudgetSummary(
            Integer month,
            Integer year,
            BigDecimal actual) {
        this.month = month;
        this.year = year;
        this.actual = actual;
    }

}
