package com.factotum.oaka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BudgetSummary {

    private Set<Long> budgetIds;

    private String category;

    private Integer categoryId;

    private String transactionType;

    private Integer transactionTypeId;

    private BigDecimal planned;

}
