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
import java.time.LocalDateTime;

/**
 * Financial transactions
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Transaction implements Serializable {

    @Id
    private Long id;
    private Long accountId;
    private Long budgetId;
    private TransactionCategory category;
    private TransactionType type;
    private RecurringTransaction recurringTransaction;
    private LocalDateTime date;
    private String description;
    private BigDecimal amount;

}
