package com.factotum.transactionservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table("transaction_sub_category")
public class TransactionSubCategory implements Serializable {

    @Id
    @Column("transaction_sub_category_id")
    private Long id;

    @Column("sub_category_name")
    private String name;

}
