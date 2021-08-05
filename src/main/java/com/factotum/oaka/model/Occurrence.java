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
    private Integer id;

    @Column("occurrence")
    private String name;

}
