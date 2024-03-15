package com.factotum.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

/**
 * Sub categories of transactions, tied to transaction category
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table("transaction_category")
public class TransactionCategory implements Serializable {

    @Id
    @Column("transaction_category_id")
    private UUID id;

    @Column("category_name")
    private String name;

    @Column("transaction_sub_category_id")
    private UUID budgetSubCategoryId;


}
