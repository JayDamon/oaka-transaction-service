package com.factotum.oaka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class FrequencyType implements Serializable {

    @Id
    private Integer id;
    private String name;
    // Factor used to multiply amount by to normalize to a monthly value
    private Double monthFactor;

}
