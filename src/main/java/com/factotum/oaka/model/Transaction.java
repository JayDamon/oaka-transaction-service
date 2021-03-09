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
 * Financial transactions
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("transaction")
public class Transaction implements Serializable {

    @Id
    @Column("transaction_id")
    private Long id;

    @Column("account_id")
    private Long accountId;

    @Column("budget_id")
    private Long budgetId;

    @Column("transaction_category_id")
    private Long transactionCategory;

    @Column("transaction_type_id")
    private Integer transactionType;

    @Column("recurring_transaction_id")
    private Long recurringTransaction;

    @Column("transaction_date")
    private LocalDateTime date;

    @Column("description")
    private String description;

    @Column("amount")
    private BigDecimal amount;
}
