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

/**
 * Income, expense // TODO this should be an enum
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("transaction_type")
public class TransactionType implements Serializable {

    @Id
    @Column("transaction_type_id")
    private Integer id;

    @Column("transaction_type")
    private String transactionTypeName;

}
