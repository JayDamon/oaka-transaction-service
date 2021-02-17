package com.protean.moneymaker.oaka.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecurringTransactionDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String recurringTransactionName;

    @JsonProperty("account")
    private ShortAccountDto account;

    @JsonProperty("subCategory")
    private BudgetSubCategoryDto budgetSubCategory;

    @JsonProperty("transactionCategory")
    private TransactionCategoryDto transactionCategory;

    @JsonProperty("frequencyType")
    private String frequencyTypeName;

    @JsonProperty("frequency")
    private Integer frequency;

    @JsonProperty("occurrence")
    private String occurrenceName;

    @JsonProperty("transactionType")
    private String transactionTypeName;

    @JsonProperty("startDate")
    private ZonedDateTime startDate;

    @JsonProperty("endDate")
    private ZonedDateTime endDate;

    @JsonProperty("amount")
    private BigDecimal amount;

}
