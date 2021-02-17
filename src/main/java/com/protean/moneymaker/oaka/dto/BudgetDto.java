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
public class BudgetDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("budgetCategory")
    private BudgetCategoryDto budgetCategory;

    @JsonProperty("startDate")
    private ZonedDateTime startDate;

    @JsonProperty("endDate")
    private ZonedDateTime endDate;

    @JsonProperty("frequencyTypeId")
    private Integer frequencyTypeId;

    @JsonProperty("frequencyType")
    private String frequencyTypeName;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("inUse")
    private Boolean inUse;

}
