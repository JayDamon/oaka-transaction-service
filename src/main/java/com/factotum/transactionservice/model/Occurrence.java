package com.factotum.transactionservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

/**
 * When in the period is the item applied. i.e. Specific Date, First of Month, Last of Month
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("occurrence")
public class Occurrence implements Serializable {

    @Id
    @Column("occurrence_id")
    private UUID id;

    @Column("occurrence")
    private String name;

}
