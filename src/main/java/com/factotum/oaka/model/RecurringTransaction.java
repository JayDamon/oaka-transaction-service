package com.factotum.oaka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Recurring transactions that are applied on a defined time period that are not bills
 * These may be better defined as allocations
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("recurring_transaction")
public class RecurringTransaction implements Serializable {

    @Id
    @Column("recurring_transaction_id")
    private Long id;

    @Column("name")
    private String recurringTransactionName;

    @Column("account_id")
    private Long account;

    @Column("budget_category_id")
    private Integer budgetSubCategory;

    @Column("transaction_category_id")
    private Long transactionCategory;

    @Column("frequency_type_id")
    private Integer frequencyType;

    // This is the number of days, months or years between occurrences based on the FrequencyType
    @Column("frequency")
    private Integer frequency;

    @Column("occurrence_id")
    private Integer occurrence;

    @Column("transaction_type_id")
    private Integer transactionType;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("amount")
    private BigDecimal amount;

}
