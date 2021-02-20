package com.factotum.oaka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Financial transactions
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "budget_id")
    private Long budgetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_category_id")
    private TransactionCategory transactionCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_transaction_id")
    private RecurringTransaction recurringTransaction;

    @Column(name = "transaction_date")
    private ZonedDateTime date;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;
}
