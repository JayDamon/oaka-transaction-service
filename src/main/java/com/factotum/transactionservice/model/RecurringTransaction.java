package com.factotum.transactionservice.model;

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
import java.util.UUID;

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
    private UUID id;

    @Column("name")
    private String recurringTransactionName;

    @Column("account_id")
    private UUID account;

    @Column("budget_category_id")
    private Integer budgetSubCategory;

    @Column("transaction_category_id")
    private UUID transactionCategory;

    @Column("frequency_type_id")
    private UUID frequencyType;

    /**
     * This is the number of days, months or years between occurrences based on the FrequencyType
     */
    @Column("frequency")
    private Integer frequency;

    @Column("occurrence_id")
    private UUID occurrence;

    @Column("transaction_type_id")
    private UUID transactionType;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("amount")
    private BigDecimal amount;

    @Column("tenant_id")
    private String tenantId;

}
