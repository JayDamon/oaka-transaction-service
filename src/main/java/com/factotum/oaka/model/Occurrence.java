package com.factotum.oaka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * When in the period is the item applied. i.e. Specific Date, First of Month, Last of Month
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Occurrence implements Serializable {

    @Id
    private Integer id;
    private String name;

}
