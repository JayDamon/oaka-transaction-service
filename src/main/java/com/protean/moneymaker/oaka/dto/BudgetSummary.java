package com.protean.moneymaker.oaka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BudgetSummary {

    private String category;

    private Integer categoryId;

    private String transactionType;

    private Integer transactionTypeId;

    private BigDecimal planned;

}
