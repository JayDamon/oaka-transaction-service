package com.factotum.oaka.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("account")
    @NotNull
    private ShortAccountDto account;

    @JsonProperty("budget")
    private BudgetDto budget;

    @JsonProperty("category")
    private TransactionCategoryDto transactionCategory;

}
