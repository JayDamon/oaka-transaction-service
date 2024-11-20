package com.factotum.transactionservice.dto;

import com.factotum.transactionservice.message.PersonalFinanceCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("amount")
    @NotNull
    private BigDecimal amount;

    @JsonProperty("description")
    @NotNull
    private String description;

    @JsonProperty("date")
    @NotNull
    @DateTimeFormat(pattern = "dd/mm/YYYY")
    private LocalDate date;

    @JsonProperty("account")
    @NotNull
    private ShortAccountDto account;

    @JsonProperty("budget")
    private BudgetDto budget;

    @JsonProperty("category")
    private PersonalFinanceCategory personalFinanceCategory;

}
