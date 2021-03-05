package com.factotum.oaka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Recurring transactions that are applied on a defined time period that are not bills
 * These may be better defined as allocations
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class RecurringTransaction implements Serializable {

    @Id
    private Long id;
    private String recurringTransactionName;
    private Long account;
    private BudgetSubCategory budgetSubCategory;
    private TransactionCategory transactionCategory;
    private FrequencyType frequencyType;
    // This is the number of days, months or years between occurrences based on the FrequencyType
    private Integer frequency;
    private Occurrence occurrence;
    private TransactionType transactionType;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private BigDecimal amount;

}
