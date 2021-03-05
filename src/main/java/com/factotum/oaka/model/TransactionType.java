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
 * Income, expense // TODO this should be an enum
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class TransactionType implements Serializable {

    @Id
    private Integer id;
    private String transactionTypeName;

}
