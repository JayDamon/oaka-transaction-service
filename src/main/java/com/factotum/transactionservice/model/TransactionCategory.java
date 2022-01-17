package com.factotum.transactionservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

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
    private Long id;

    @Column("category_name")
    private String name;

    @Column("transaction_sub_category_id")
    private Long budgetSubCategoryId;


}
