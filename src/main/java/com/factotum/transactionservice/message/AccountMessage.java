package com.factotum.transactionservice.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountMessage implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

}
