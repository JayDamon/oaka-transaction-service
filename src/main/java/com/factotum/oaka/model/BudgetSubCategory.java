package com.factotum.oaka.model;

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

/**
 * i.e. Primary transaction category // TODO fix this comment
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table("budget_sub_category")
public class BudgetSubCategory implements Serializable {

    @Id
    @Column("budget_sub_category_id")
    private Long id;

    @Column("sub_category_name")
    private String name;

}
