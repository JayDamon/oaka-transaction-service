package com.protean.moneymaker.oaka.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetCategoryDto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("type")
    private String typeName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("budgetItems")
    private Set<BudgetItemDto> budgetItems;

}
